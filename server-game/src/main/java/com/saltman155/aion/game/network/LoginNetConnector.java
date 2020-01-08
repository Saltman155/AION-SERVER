package com.saltman155.aion.game.network;

import com.saltman155.aion.game.model.configure.LoginNetwork;
import com.saltman155.aion.game.network.loginserver.LoginChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 与登录服务的网络连接处理启动类
 * @author saltman155
 * @date 2019/10/24 1:21
 */

@Component
public class LoginNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(LoginNetConnector.class);

    private final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    private final LoginNetwork loginNetwork;
    private final LoginChannelInitializer channelInitializer;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel loginChannel;

    public LoginNetConnector(LoginNetwork loginNetwork, LoginChannelInitializer channelInitializer) {
        this.loginNetwork = loginNetwork;
        this.channelInitializer = channelInitializer;
    }


    public synchronized void start() throws InterruptedException {
        if(group != null){
            logger.warn("与登录服务器的网络连接服务已启动！");
            return;
        }
        group = new NioEventLoopGroup(loginNetwork.getThread());
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(loginNetwork.getRemoteAddr(),loginNetwork.getRemotePort()))
                .handler(channelInitializer);
        service.scheduleAtFixedRate(this::connect,0,3000, TimeUnit.MILLISECONDS);
    }

    private void connect(){
        if(loginChannel == null || !loginChannel.isOpen()) {
            logger.info("尝试与登录服务器连接...");
            try {
                ChannelFuture future = bootstrap.connect().sync();
                future.addListener(f -> {
                    if(f.isSuccess()){
                        logger.info("与登录服务器连接成功！");
                        loginChannel = ((ChannelFuture)f).channel();
                        loginChannel.closeFuture().addListener(f2->{
                           logger.error("与登录服务器的连接中断！");
                           loginChannel = null;
                           //做一些关闭的动作
                        });
                    }
                });
            } catch (Exception ignore) { }
        }
    }

}
