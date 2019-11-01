package com.saltman155.aion.login.network;

import com.saltman155.aion.login.network.handler.client.ClientChannelInitializer;
import com.saltman155.aion.login.network.handler.gameserver.GameChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * 主服务端端网络连接处理服务启动类
 * @author saltman155
 * @date 2019/10/23 2:52
 */

@Component
@PropertySource(value = {"file:./config/network/network.properties"})
public class GameNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    @Value("${loginServer.main.port}")
    private Integer mainBindPort;
    @Value("${loginServer.main.threads}")
    private Integer threadCount;

    @Resource
    private GameChannelInitializer channelInitializer;

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threadCount);
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(mainBindPort))
                    .childHandler(channelInitializer);
            ChannelFuture f = bootstrap.bind().sync();
            logger.info("登录服务器已在端口 {} 上开启游戏主服务端连接监听！",mainBindPort);
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
