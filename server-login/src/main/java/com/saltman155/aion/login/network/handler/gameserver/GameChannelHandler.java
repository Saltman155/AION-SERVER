package com.saltman155.aion.login.network.handler.gameserver;

import com.saltman155.aion.login.network.mainserver.GSChannelAttr;
import com.saltman155.aion.login.network.mainserver.MainPacket;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 游戏主服务端连接处理器
 * @author saltman155
 * @date 2019/10/26 1:12
 */

@Component
@ChannelHandler.Sharable
@PropertySource(value = {"file:./config/network/network.properties"})
public class GameChannelHandler extends SimpleChannelInboundHandler<MainPacket> {

    private static final Logger logger = LoggerFactory.getLogger(GameChannelHandler.class);

    @Value("${loginServer.enable.gameServer.pingpong}")
    private Boolean login2GamePingPong;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String ip = ChannelUtil.getIp(channel);
        channel.attr(GSChannelAttr.SESSION_STATE).set(GSChannelAttr.SessionState.CONNECTED);
        if(login2GamePingPong){

        }
        logger.info("ip为 {} 的游戏服务端尝试建立连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MainPacket msg) throws Exception {

    }



}
