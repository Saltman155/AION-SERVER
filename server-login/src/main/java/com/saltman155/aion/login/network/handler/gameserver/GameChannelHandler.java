package com.saltman155.aion.login.network.handler.gameserver;

import com.saltman155.aion.login.model.configure.Network;
import com.saltman155.aion.login.network.mainserver.GSChannelAttr;
import com.saltman155.aion.login.network.mainserver.MainPacket;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GameChannelHandler extends SimpleChannelInboundHandler<MainPacket> {

    private static final Logger logger = LoggerFactory.getLogger(GameChannelHandler.class);

    private final Network network;

    public GameChannelHandler(Network network) {
        this.network = network;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String ip = ChannelUtil.getIp(channel);
        channel.attr(GSChannelAttr.SESSION_STATE).set(GSChannelAttr.SessionState.CONNECTED);
        if(network.mainServer.isPingpongCheck()){
            //建立心跳
        }
        logger.info("ip为 {} 的游戏服务端尝试建立连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MainPacket msg) throws Exception {

    }



}
