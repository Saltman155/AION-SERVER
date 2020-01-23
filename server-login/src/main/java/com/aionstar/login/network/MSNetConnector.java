package com.aionstar.login.network;

import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.handler.MSChannelInitializer;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


/**
 * 主服务器网络连接处理服务启动类
 * @author saltman155
 * @date 2019/10/23 2:52
 */

public class MSNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);


    public static void start() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(NetworkConfigure.MS_WORKER_THREAD);
        EventLoopGroup worker = new NioEventLoopGroup(NetworkConfigure.MS_WORKER_THREAD);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(NetworkConfigure.MS_PORT))
                .childHandler(new MSChannelInitializer())
                .childAttr(MSChannelAttr.M_SESSION_STATE,MSChannelAttr.InnerSessionState.CONNECTED);
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("登录服务器已在端口 {} 上开启游戏主服务器连接监听！",NetworkConfigure.MS_PORT);
    }

}
