package com.aionstar.login.network.handler;

import com.aionstar.commons.network.codec.InnerPacketDecoder;
import com.aionstar.commons.network.codec.InnerPacketEncoder;
import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.factories.MSPacketFactory;
import com.aionstar.login.network.handler.mainserver.MSMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 设置来自主服务器的封包的处理类
 * @author saltman155
 * @date 2019/10/24 3:47
 */

public class MSChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出站数据包编码器
        pipeline.addLast(new InnerPacketEncoder());
        //入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder(NetworkConfigure.MS_BUFFER_SIZE));
        //入站数据包解码器
        pipeline.addLast(new InnerPacketDecoder(MSPacketFactory.getInstance()));
        //主服务端数据包处理器
        pipeline.addLast(new MSMessageHandler());
    }

}
