package com.aionstar.game.network.handler.client;

import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.client.serverpackets.SM_KEY;
import com.aionstar.game.network.crypt.ClientCrypt;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端消息处理器
 * @author saltman155
 * @date 2020/1/23 21:27
 */

@ChannelHandler.Sharable
public class ClientMessageHandler extends SimpleChannelInboundHandler<AionClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("游戏客户端尝试连接！");
        Channel channel = ctx.channel();
        channel.attr(ClientChannelAttr.BUFFER).set(Unpooled.buffer(NetworkConfigure.CLIENT_BUFFER_SIZE));
        ClientCrypt crypt = new ClientCrypt();
        channel.attr(ClientChannelAttr.CRYPT).set(crypt);
        channel.writeAndFlush(new SM_KEY(crypt.enableKey()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AionClientPacket msg) throws Exception {

    }

}
