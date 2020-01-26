package com.aionstar.game.network.handler;

import com.aionstar.commons.network.codec.InnerPacketDecoder;
import com.aionstar.commons.network.codec.InnerPacketEncoder;
import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.handler.loginserver.LSMessageHandler;
import com.aionstar.game.network.loginserver.LSPacketFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * 登录服务器连接需要的handler设置
 * @author saltman155
 * @date 2019/10/24 1:49
 */

public class LSChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new InnerPacketEncoder());
        ch.pipeline().addLast(new PacketFrameDecoder(NetworkConfigure.LS_BUFFER_SIZE));
        ch.pipeline().addLast(new InnerPacketDecoder(LSPacketFactory.getInstance()));
        ch.pipeline().addLast(new LSMessageHandler());
    }

}
