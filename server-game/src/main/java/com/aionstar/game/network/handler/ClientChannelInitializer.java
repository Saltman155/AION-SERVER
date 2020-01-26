package com.aionstar.game.network.handler;

import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.client.ClientPacketFactory;
import com.aionstar.game.network.handler.client.ClientMessageDecoder;
import com.aionstar.game.network.handler.client.ClientMessageEncoder;
import com.aionstar.game.network.handler.client.ClientMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端连接需要的handler设置
 * @author saltman155
 * @date 2020/1/18 19:02
 */

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 服务端出站封包编码
        pipeline.addLast(new ClientMessageEncoder());
        // 服务端入站Frame解码 （黏/分包处理）
        pipeline.addLast(new PacketFrameDecoder(NetworkConfigure.CLIENT_BUFFER_SIZE));
        pipeline.addLast(new ClientMessageDecoder(ClientPacketFactory.getInstance()));
        // 服务端入站消息业务处理
        pipeline.addLast(new ClientMessageHandler());
    }

}
