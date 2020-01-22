package com.aionstar.login.network;

import com.aionstar.login.model.configure.Network;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.handler.ClientChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * 客户端网络连接处理服务启动类
 * @author: saltman155
 * @date: 2019/3/20 18:00
 */
@Component
public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    private final Network network;

    private final ClientChannelInitializer channelInitializer;

    public ClientNetConnector(Network network, ClientChannelInitializer channelInitializer) {
        this.network = network;
        this.channelInitializer = channelInitializer;
    }

    @PostConstruct
    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(network.client.getBossThread());
        EventLoopGroup workerGroup = new NioEventLoopGroup(network.client.getWorkerThread());
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(network.client.getPort()))
                .childHandler(channelInitializer)
                .childAttr(ClientChannelAttr.C_SESSION_STATE, ClientChannelAttr.SessionState.CONNECTED);
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("登录服务器已在端口 {} 上开启游戏客户端连接监听！",network.client.getPort());
    }

}
