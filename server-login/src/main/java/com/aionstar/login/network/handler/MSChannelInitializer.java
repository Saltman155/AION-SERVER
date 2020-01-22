package com.aionstar.login.network.handler;

import com.aionstar.commons.network.codec.InnerPacketDecoder;
import com.aionstar.commons.network.codec.InnerPacketEncoder;
import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.login.model.configure.Network;
import com.aionstar.login.network.factories.MSPacketFactory;
import com.aionstar.login.network.handler.mainserver.MSMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 设置来自主服务器的封包的处理类
 * @author saltman155
 * @date 2019/10/24 3:47
 */

@Component
public class MSChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final MSMessageHandler msMessageHandler;
    private final MSPacketFactory msPacketFactory;
    private final Network network;

    @Autowired
    public MSChannelInitializer(MSMessageHandler msMessageHandler,
                                MSPacketFactory msPacketFactory,
                                Network network) {
        this.msMessageHandler = msMessageHandler;
        this.msPacketFactory = msPacketFactory;
        this.network = network;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出站数据包编码器
        pipeline.addLast(new InnerPacketEncoder());
        //入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder(network.mainServer.getBufferSize()));
        //入站数据包解码器
        pipeline.addLast(new InnerPacketDecoder(msPacketFactory));
        //主服务端数据包处理器
        pipeline.addLast(msMessageHandler);
    }

}
