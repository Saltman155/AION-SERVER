package com.aionstar.chat.network;

import com.aionstar.chat.config.NetworkConfigure;
import com.aionstar.chat.network.handler.ClientChannelInitializer;
import com.aionstar.chat.network.mainserver.MSChannelAttr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class MSNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(MSNetConnector.class);

    public static void open() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(NetworkConfigure.MS_WORKER_THREAD);
        EventLoopGroup worker = new NioEventLoopGroup(NetworkConfigure.MS_WORKER_THREAD);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(NetworkConfigure.MS_PORT))
                .childHandler(new ClientChannelInitializer())
                .childAttr(MSChannelAttr.M_SESSION_STATE, MSChannelAttr.InnerSessionState.CONNECTED);
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("聊天服务器已在端口 {} 上开启游戏主服务器连接监听！",NetworkConfigure.MS_PORT);
    }

}