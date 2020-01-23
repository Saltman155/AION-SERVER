package com.aionstar.login.network.handler.client;

import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.crypt.EncryptedRSAKeyPair;
import com.aionstar.login.network.crypt.LKeyGenerator;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.network.client.serverpackets.SM_INIT;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

/**
 * 客户端消息处理器
 * @author saltman155
 * @date 2019/10/10 1:29
 */

@ChannelHandler.Sharable
public class ClientChannelHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientChannelHandler.class);

    /**客户端连接的业务操作线程池*/
    private static final ExecutorService processor =
            new ThreadPoolExecutor(1, 8, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), (ThreadFactory) Thread::new);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("收到来自IP %s 的连接请求！",getIp(ctx)));
        EncryptedRSAKeyPair keyPair = LKeyGenerator.getEncryptedRSAKeyPair();
        SecretKey blowfishKey = LKeyGenerator.generateBlowfishKey();
        String sessionId = ctx.channel().id().asLongText();
        //设置临时空间
        ctx.channel().attr(ClientChannelAttr.BUFFER).set(Unpooled.buffer(NetworkConfigure.CLIENT_BUFFER_SIZE));
        //发送初始数据包
        ctx.writeAndFlush(new SM_INIT(blowfishKey,keyPair,sessionId.hashCode()));
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
