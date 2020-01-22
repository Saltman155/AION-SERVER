package com.aionstar.game.network;

import com.aionstar.game.model.configure.ClientNetwork;
import com.aionstar.game.network.handler.ClientChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 客户端网络连接处理服务启动类
 * @author saltman155
 * @date 2019/10/24 1:02
 */

@Component
public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    private final ClientChannelInitializer channelInitializer;

    public ClientNetConnector(ClientChannelInitializer channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    public void start() {
        ClientNetwork clientNetwork = ClientNetwork.getInstance();
        EventLoopGroup bossGroup = new NioEventLoopGroup(clientNetwork.getBossTread());
        EventLoopGroup workerGroup = new NioEventLoopGroup(clientNetwork.getWorkerThread());
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(clientNetwork.getPort()))
                    .childHandler(channelInitializer);
            ChannelFuture f = bootstrap.bind().sync();
            logger.info("游戏主服务器已在端口 {} 上开启游戏客户端连接监听！",clientNetwork.getPort());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
