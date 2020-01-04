package com.saltman155.aion.login.network;

import com.saltman155.aion.login.model.configure.Network;
import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.handler.client.ClientChannelInitializer;
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
import java.nio.ByteBuffer;

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
                .childAttr(ClientChannelAttr.SESSION_STATE,ClientChannelAttr.SessionState.CONNECTED)
                .childAttr(ClientChannelAttr.WRITE_TMP, ByteBuffer.allocate(network.client.getWriteBufferSize()))
                .childAttr(ClientChannelAttr.READ_TMP,ByteBuffer.allocate(network.client.getReadBufferSize()));
        ChannelFuture f = bootstrap.bind().sync();
        logger.info("登录服务器已在端口 {} 上开启游戏客户端连接监听！",network.client.getPort());
    }

}
