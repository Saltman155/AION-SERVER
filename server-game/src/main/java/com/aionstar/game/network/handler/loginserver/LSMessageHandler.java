package com.aionstar.game.network.handler.loginserver;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.network.loginserver.LSChannelAttr;
import com.aionstar.game.network.loginserver.LSManager;
import com.aionstar.game.network.loginserver.serverpackets.SM_GS_AUTH;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 登录服务器消息处理器
 * @author saltman155
 * @date 2020/1/17 1:10
 */

@ChannelHandler.Sharable
public class LSMessageHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private static final Logger logger = LoggerFactory.getLogger(LSMessageHandler.class);

    /**服务端消息业务处理线程池*/
    private static final ExecutorService processor =
            new ThreadPoolExecutor(3, 8, 0,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<>(), (ThreadFactory) Thread::new);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //分配一个读写空间给该通道
        channel.attr(LSChannelAttr.BUFFER).set(Unpooled.buffer(NetworkConfigure.LS_BUFFER_SIZE));
        //登记到LSManager中
        LSManager.getInstance().loginServerRegister(channel);
        //发送登录服务器验证数据包
        ctx.writeAndFlush(new SM_GS_AUTH());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket msg) throws Exception {
        //异步处理数据包
        if(msg.readable()){
            processor.execute(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("登录服务器断开！");
        //LSManager解除注册
        LSManager.getInstance().loginServerUnregister();
    }
}
