package com.saltman155.aion.login.network.handler.client;

import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.ClientPacket;
import com.saltman155.aion.login.network.client.serverpackets.request.SM_INIT_REQUEST;
import com.saltman155.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.saltman155.aion.login.network.crypt.LKeyGenerator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

/**
 * 客户端消息处理器
 * @author saltman155
 * @date 2019/10/10 1:29
 */

@Component
@ChannelHandler.Sharable
class ClientChannelHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientChannelHandler.class);

    /**客户端连接的业务操作线程池*/
    private static final ExecutorService processor =
            new ThreadPoolExecutor(1, 8, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), (ThreadFactory) Thread::new);

    @Resource
    private LKeyGenerator keyGenerator;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("收到来自IP %s 的连接请求！",getIp(ctx)));
        //设置连接状态
        ctx.channel().attr(ClientChannelAttr.SESSION_STATE).set(ClientChannelAttr.SessionState.CONNECTED);
        EncryptedRSAKeyPair keyPair = keyGenerator.getEncryptedRSAKeyPair();
        SecretKey blowfishKey = keyGenerator.generateBlowfishKey();
        String sessionId = ctx.channel().id().asLongText();
        //发送初始数据包
        ctx.writeAndFlush(new SM_INIT_REQUEST(blowfishKey,keyPair,sessionId.hashCode()));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket packet) throws Exception {
        logger.info("收到客户端数据包！opcode：{}",packet.getOpcode());
        if(packet.readable()){
            processor.submit(packet);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info(String.format("IP为 %s 的连接断开！",getIp(ctx)));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    private String getIp(ChannelHandlerContext ctx){
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        return address.getAddress().getHostAddress();
    }

}
