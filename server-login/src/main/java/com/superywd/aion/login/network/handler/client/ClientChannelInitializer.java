package com.superywd.aion.login.network.handler.client;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 连接handler初始化
 * @author saltman155
 * @date 2019/10/10 1:05
 */

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private ClientChannelHandler clientChannelHandler;
    @Resource
    private ClientMessageEncoder clientMessageEncoder;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline().addLast();
        //服务端出站消息编码器
        pipeline.addLast(clientMessageEncoder);
        //客户端入站消息解码器
        pipeline.addLast(new ClientMessageDecoder());
        //客户端数据包处理
        pipeline.addLast(clientChannelHandler);
    }
}
