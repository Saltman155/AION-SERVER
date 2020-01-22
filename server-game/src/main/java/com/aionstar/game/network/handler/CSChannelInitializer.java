package com.aionstar.game.network.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author saltman155
 * @date 2020/1/18 19:03
 */

@Component
public class CSChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

    }
}
