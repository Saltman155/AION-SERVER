package com.aionstar.login.network;

import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.handler.ClientChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 客户端网络连接处理服务启动类
 * @author: saltman155
 * @date: 2019/3/20 18:00
 */

public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);


    public static void open() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_BOSS_THREAD);
        EventLoopGroup workerGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_WORKER_THREAD);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(NetworkConfigure.CLIENT_PORT))
                .childHandler(new ClientChannelInitializer())
                .childAttr(ClientChannelAttr.C_SESSION_STATE, ClientChannelAttr.SessionState.CONNECTED);
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("登录服务器已在端口 {} 上开启游戏客户端连接监听！",NetworkConfigure.CLIENT_PORT);
    }

}
