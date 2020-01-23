package com.aionstar.game.network.handler;

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
        pipeline.addLast(new ClientMessageHandler());
    }

}
