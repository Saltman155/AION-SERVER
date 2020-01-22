package com.aionstar.login.network.handler;

import com.aionstar.login.model.configure.Network;
import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.login.network.factories.ClientPacketFactory;
import com.aionstar.login.network.handler.client.ClientChannelHandler;
import com.aionstar.login.network.handler.client.ClientMessageDecoder;
import com.aionstar.login.network.handler.client.ClientMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 连接handler初始化
 * @author saltman155
 * @date 2019/10/10 1:05
 */

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ClientChannelHandler clientChannelHandler;
    private final ClientMessageEncoder clientMessageEncoder;
    private final ClientPacketFactory clientPacketHandler;
    private final Network network;

    @Autowired
    public ClientChannelInitializer(ClientChannelHandler clientChannelHandler,
                                    ClientMessageEncoder clientMessageEncoder,
                                    ClientPacketFactory clientPacketHandler,
                                    Network network) {
        this.clientChannelHandler = clientChannelHandler;
        this.clientMessageEncoder = clientMessageEncoder;
        this.clientPacketHandler = clientPacketHandler;
        this.network = network;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //服务端出站消息编码器
        pipeline.addLast(clientMessageEncoder);
        //客户端入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder(network.client.getBufferSize()));
        //客户端入站消息解密
        pipeline.addLast(new ClientMessageDecoder(clientPacketHandler));
        //客户端数据包处理
        pipeline.addLast(clientChannelHandler);
    }
}
