package com.saltman155.aion.login.network.handler.gameserver;

import com.saltman155.aion.login.network.gameserver.GamePacket;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 游戏主服务端连接处理器
 * @author saltman155
 * @date 2019/10/26 1:12
 */

@Component
@ChannelHandler.Sharable
public class GameChannelHandler extends SimpleChannelInboundHandler<GamePacket> {

    private static final Logger logger = LoggerFactory.getLogger(GameChannelHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String ip = ChannelUtil.getIp(channel);
        logger.info("游戏主服务端 {} 尝试连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GamePacket msg) throws Exception {

    }



}
