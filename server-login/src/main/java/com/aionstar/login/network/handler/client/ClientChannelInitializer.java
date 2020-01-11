package com.aionstar.login.network.handler.client;

import com.aionstar.login.model.configure.Network;
import com.saltman155.aion.commons.network.codec.PacketFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
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
    private final Network network;

    public ClientChannelInitializer(ClientChannelHandler clientChannelHandler,
                                    ClientMessageEncoder clientMessageEncoder,
                                    Network network) {
        this.clientChannelHandler = clientChannelHandler;
        this.clientMessageEncoder = clientMessageEncoder;
        this.network = network;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //服务端出站消息编码器
        pipeline.addLast(clientMessageEncoder);
        //客户端入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder(network.client.getReadBufferSize()));
        //客户端入站消息解密
        pipeline.addLast(new ClientMessageDecoder());
        //客户端数据包处理
        pipeline.addLast(clientChannelHandler);
    }
}
