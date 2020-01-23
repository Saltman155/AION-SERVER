package com.aionstar.login.network.handler.mainserver;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.config.NetworkConfigure;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 游戏主服务端连接处理器
 * @author saltman155
 * @date 2019/10/26 1:12
 */

@ChannelHandler.Sharable
public class MSMessageHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(MSMessageHandler.class);

    /**服务端消息业务处理线程池*/
    private static final ExecutorService processor =
            new ThreadPoolExecutor(3, 8, 0,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<>(), (ThreadFactory) Thread::new);


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //设置临时读写空间
        channel.attr(MSChannelAttr.BUFFER).set(Unpooled.buffer(NetworkConfigure.MS_BUFFER_SIZE));
        String ip = ChannelUtil.getIp(channel);
        if(NetworkConfigure.PINGPONG_CHECK){
            //建立心跳
        }
        logger.info("ip为 {} 的游戏服务端尝试建立连接！",ip);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket packet) throws Exception {
        Channel channel = ctx.channel();
        if(packet.readable()){
            processor.execute(packet);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
