package com.aionstar.game.network.handler.loginserver;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.loginserver.LSChannelAttr;
import com.aionstar.game.network.loginserver.serverpackets.SM_GS_AUTH;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author saltman155
 * @date 2020/1/17 1:10
 */

@ChannelHandler.Sharable
public class LSMessageHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(LSMessageHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //分配一个连接空间给该通道
        channel.attr(LSChannelAttr.BUFFER).set(Unpooled.buffer(NetworkConfigure.LS_BUFFER_SIZE));
        //发送验证数据包
        ctx.writeAndFlush(new SM_GS_AUTH());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket msg) throws Exception {

    }
}
