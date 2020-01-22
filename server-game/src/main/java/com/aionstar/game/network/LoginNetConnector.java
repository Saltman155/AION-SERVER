package com.aionstar.game.network;

import com.aionstar.game.model.configure.LoginNetwork;
import com.aionstar.game.network.handler.LSChannelInitializer;
import com.aionstar.game.network.loginserver.LSChannelAttr;
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

    private final LSChannelInitializer channelInitializer;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel loginChannel;

    public LoginNetConnector(LSChannelInitializer channelInitializer) {
        this.channelInitializer = channelInitializer;
    }


    public synchronized void start() throws InterruptedException {
        if(group != null){
            logger.warn("与登录服务器的网络连接服务已启动！");
            return;
        }
        LoginNetwork loginNetwork = LoginNetwork.getInstance();
        group = new NioEventLoopGroup(loginNetwork.getThread());
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(loginNetwork.getRemoteAddr(),loginNetwork.getRemotePort()))
                .attr(LSChannelAttr.LS_SESSION_STATE,LSChannelAttr.InnerSessionState.CONNECTED)
                .handler(channelInitializer);
        service.scheduleAtFixedRate(this::connect,0,3000, TimeUnit.MILLISECONDS);
    }

    private void connect(){
        if(loginChannel == null || !loginChannel.isOpen()) {
            logger.info("尝试与登录服务器连接...");
            try {
                ChannelFuture future = bootstrap.connect().sync();
                future.addListener(f1 -> {
                    if(f1.isSuccess()){
                        logger.info("与登录服务器连接成功！");
                        loginChannel = ((ChannelFuture)f1).channel();
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
