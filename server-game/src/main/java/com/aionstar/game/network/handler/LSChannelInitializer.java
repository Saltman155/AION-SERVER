package com.aionstar.game.network.handler;

import com.aionstar.commons.network.codec.InnerPacketDecoder;
import com.aionstar.commons.network.codec.InnerPacketEncoder;
import com.aionstar.commons.network.codec.PacketFrameDecoder;
import com.aionstar.game.model.configure.LoginNetwork;
import com.aionstar.game.network.handler.loginserver.LSMessageHandler;
import com.aionstar.game.network.loginserver.LSPacketFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 登录服务器连接需要的handler设置
 * @author saltman155
 * @date 2019/10/24 1:49
 */

@Component
public class LSChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final LSMessageHandler lsMessageHandler;
    private final LSPacketFactory lsPacketFactory;

    @Autowired
    public LSChannelInitializer(LSMessageHandler lsMessageHandler,
                                LSPacketFactory lsPacketFactory) {
        this.lsMessageHandler = lsMessageHandler;
        this.lsPacketFactory = lsPacketFactory;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new InnerPacketEncoder());
        ch.pipeline().addLast(new PacketFrameDecoder(LoginNetwork.getInstance().getBufferSize()));
        ch.pipeline().addLast(new InnerPacketDecoder(lsPacketFactory));
        ch.pipeline().addLast(lsMessageHandler);
    }

}
