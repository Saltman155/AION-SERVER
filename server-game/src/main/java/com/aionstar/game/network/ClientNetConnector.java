package com.aionstar.game.network;

import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.handler.CSChannelInitializer;
import com.aionstar.game.network.handler.ClientChannelInitializer;
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
 * @author saltman155
 * @date 2019/10/24 1:02
 */

public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);


    public static void open() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_BOSS_THREAD);
        EventLoopGroup workerGroup = new NioEventLoopGroup(NetworkConfigure.CLIENT_WORKER_THREAD);
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(NetworkConfigure.CLIENT_PORT))
                    .childHandler(new ClientChannelInitializer());
            ChannelFuture f = bootstrap.bind().sync();
            logger.info("游戏主服务端的连接服务已启动，暴露端口 {} .",NetworkConfigure.CLIENT_PORT);
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
