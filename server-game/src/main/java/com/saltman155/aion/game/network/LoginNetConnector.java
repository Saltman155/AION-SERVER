package com.saltman155.aion.game.network;

import com.saltman155.aion.game.network.loginserver.LoginChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * 与登录服务的网络连接处理启动类
 * @author saltman155
 * @date 2019/10/24 1:21
 */

@Component
@PropertySource(value = {"file:./config/network/network.properties"})
public class LoginNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(LoginNetConnector.class);

    @Resource
    private LoginChannelInitializer channelInitializer;

    @Value("${gameserver.network.login.host}")
    private String loginServerHost;
    @Value("${gameserver.network.login.port}")
    private Integer loginServerPort;

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(loginServerHost,loginServerPort))
                .handler(channelInitializer);
        new Thread(()->{
            ChannelFuture future;
            while(true){
                try {
                    logger.info("等待与登录服务端建立连接...");
                    future = bootstrap.connect().sync();
                    if(future.isSuccess()){
                        future.addListener(f -> {
                           logger.info("与登录服务端建立连接！");
                            ((ChannelFuture)f).channel().closeFuture().addListener(item -> group.shutdownGracefully().sync());
                        });
                        break;
                    }
                } catch (Exception connectFail) {
                    //连接不成功，则等待3秒后继续尝试连接
                    try { Thread.sleep(3000); }
                    catch (InterruptedException ignored) { }
                }
            }}).start();

    }

}
