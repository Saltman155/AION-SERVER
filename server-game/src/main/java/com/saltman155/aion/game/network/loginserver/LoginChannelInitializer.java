package com.saltman155.aion.game.network.loginserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;


/**
 * @author saltman155
 * @date 2019/10/24 1:49
 */

@Component
public class LoginChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

    }

}
