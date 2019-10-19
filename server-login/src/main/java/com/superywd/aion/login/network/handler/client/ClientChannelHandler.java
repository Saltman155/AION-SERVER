package com.superywd.aion.login.network.handler.client;

import com.superywd.aion.login.network.aion.ClientPacket;
import com.superywd.aion.login.network.aion.serverpackets.SM_INIT;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.superywd.aion.login.network.crypt.LKeyGenerator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.net.InetSocketAddress;

/**
 * 客户端消息处理器
 * @author saltman155
 * @date 2019/10/10 1:29
 */

@Component
@ChannelHandler.Sharable
class ClientChannelHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientChannelHandler.class);

    @Resource
    private LKeyGenerator keyGenerator;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("收到来自IP %s 的连接请求！",getIp(ctx)));
        EncryptedRSAKeyPair keyPair = keyGenerator.getEncryptedRSAKeyPair();
        SecretKey blowfishKey = keyGenerator.generateBlowfishKey();
        String sessionId = ctx.channel().id().asLongText();
        ctx.writeAndFlush(new SM_INIT(blowfishKey,keyPair,sessionId.hashCode()));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket msg) throws Exception {

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
