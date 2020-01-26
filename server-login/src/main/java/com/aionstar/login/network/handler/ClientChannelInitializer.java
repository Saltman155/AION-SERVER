package com.aionstar.login.network.handler;

import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.factories.ClientPacketFactory;
import com.aionstar.login.network.handler.client.ClientChannelHandler;
import com.aionstar.login.network.handler.client.ClientMessageDecoder;
import com.aionstar.login.network.handler.client.ClientMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 连接handler初始化
 * @author saltman155
 * @date 2019/10/10 1:05
 */

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //服务端出站消息编码器
        pipeline.addLast(new ClientMessageEncoder());
        //客户端入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder(NetworkConfigure.CLIENT_BUFFER_SIZE));
        //客户端入站消息解密
        pipeline.addLast(new ClientMessageDecoder(ClientPacketFactory.getInstance()));
        //客户端数据包处理
        pipeline.addLast(new ClientChannelHandler());
    }
}
