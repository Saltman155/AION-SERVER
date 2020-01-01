package com.saltman155.aion.login.network.handler.gameserver;

import com.saltman155.aion.login.network.mainserver.LoginPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

/**
 * 登录客户端发送给服务端的数据包的编码器
 * @author saltman155
 * @date 2019/11/16 20:34
 */

@Component
@ChannelHandler.Sharable
public class GameChannelEncoder extends MessageToByteEncoder<LoginPacket> {

    /**
     * 将数据包转成二进制写出
     * 登录服务端与游戏服务端的数据通讯是明文的，所以处理比较简单，直接在数据包头两个字节写入长度就行了
     * @param ctx
     * @param packet
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, LoginPacket packet, ByteBuf out) throws Exception {
        Channel channel = ctx.channel();
        //获取数据包数据
        ByteBuffer buffer = packet.getData();
        //在头两个字节写入具体的数据长度
        buffer.flip();
        buffer.putShort((short) buffer.limit());
        buffer.position(0);
        //写出数据
        out.writeBytes(buffer);
    }


}
