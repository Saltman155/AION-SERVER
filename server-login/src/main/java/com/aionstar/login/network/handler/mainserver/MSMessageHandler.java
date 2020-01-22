package com.aionstar.login.network.handler.mainserver;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.model.configure.Network;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.buffer.Unpooled;
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
public class MSMessageHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(MSMessageHandler.class);

    private final Network network;

    public MSMessageHandler(Network network) {
        this.network = network;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //设置临时读写空间
        channel.attr(MSChannelAttr.BUFFER).set(Unpooled.buffer(network.mainServer.getBufferSize()));
        String ip = ChannelUtil.getIp(channel);
        if(network.mainServer.isPingpongCheck()){
            //建立心跳
        }
        logger.info("ip为 {} 的游戏服务端尝试建立连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket packet) throws Exception {
        Channel channel = ctx.channel();
        if(packet.readable()){
            channel.eventLoop().execute(packet);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
