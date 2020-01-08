package com.saltman155.aion.login.network.handler.mainserver;

import com.saltman155.aion.login.network.handler.PacketFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录服务器连接游戏服务器
 * @author saltman155
 * @date 2019/10/24 3:47
 */

@Component
public class MainChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private MainChannelEncoder mainChannelEncoder;
    @Resource
    private MainChannelHandler mainChannelHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出站数据包编码器
        pipeline.addLast(mainChannelEncoder);
        //入站Frame解码器
        pipeline.addLast(new PacketFrameDecoder());
        //主服务端封包处理器
        pipeline.addLast(mainChannelHandler);
    }

}
