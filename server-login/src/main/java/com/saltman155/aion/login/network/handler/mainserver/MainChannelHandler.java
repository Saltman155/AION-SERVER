package com.saltman155.aion.login.network.handler.mainserver;

import com.saltman155.aion.login.model.configure.Network;
import com.saltman155.aion.login.network.mainserver.MSChannelAttr;
import com.saltman155.aion.login.network.mainserver.MainPacket;
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
public class MainChannelHandler extends SimpleChannelInboundHandler<MainPacket> {

    private static final Logger logger = LoggerFactory.getLogger(MainChannelHandler.class);

    private final Network network;

    public MainChannelHandler(Network network) {
        this.network = network;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String ip = ChannelUtil.getIp(channel);
        if(network.mainServer.isPingpongCheck()){
            //建立心跳
        }
        logger.info("ip为 {} 的游戏服务端尝试建立连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MainPacket msg) throws Exception {

    }



}
