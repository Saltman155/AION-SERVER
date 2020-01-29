package com.aionstar.chat.network;

import com.aionstar.chat.config.NetworkConfigure;
import com.aionstar.chat.network.handler.ClientChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    public static void open() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_BOSS_THREAD);
        EventLoopGroup workerGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_WORKER_THREAD);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(NetworkConfigure.CLIENT_PORT))
                .childHandler(new ClientChannelInitializer());
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("聊天服务器已在端口 {} 上开启游戏客户端连接监听！",NetworkConfigure.CLIENT_PORT);
    }
}
