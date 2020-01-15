package com.aionstar.commons.network.codec;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

@ChannelHandler.Sharable
public class InnerPacketEncoder extends MessageToByteEncoder<ServerPacket> {

    /**
     * 将数据包转成二进制写出
     * 登录服务端与游戏服务端的数据通讯是明文的，所以处理比较简单，直接在数据包头两个字节写入长度就行了
     * @param ctx
     * @param packet
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket packet, ByteBuf out) throws Exception {
        Channel channel = ctx.channel();
        //获取绑定的buffer
        ByteBuf buffer = channel.attr(BaseChannelAttr.BUFFER).get();
        //将数据包中数据写入buffer中
        packet.writeData(buffer);
        //将buffer写出
        out.writeBytes(buffer);
    }


}