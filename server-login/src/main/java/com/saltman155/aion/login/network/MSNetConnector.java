package com.saltman155.aion.login.network;

import com.saltman155.aion.login.model.configure.Network;
import com.saltman155.aion.login.network.handler.mainserver.MainChannelInitializer;
import com.saltman155.aion.login.network.mainserver.MSChannelAttr;
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
 * 主服务器网络连接处理服务启动类
 * @author saltman155
 * @date 2019/10/23 2:52
 */

@Component
public class MSNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    private final Network network;

    private final MainChannelInitializer channelInitializer;

    public MSNetConnector(Network network, MainChannelInitializer channelInitializer) {
        this.network = network;
        this.channelInitializer = channelInitializer;
    }

    @PostConstruct
    public void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(network.mainServer.getThread());
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(network.mainServer.getPort()))
                .childHandler(channelInitializer)
                .childAttr(MSChannelAttr.M_SESSION_STATE,MSChannelAttr.SessionState.CONNECTED);
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("登录服务器已在端口 {} 上开启游戏主服务器连接监听！",network.mainServer.getPort());
    }
}
