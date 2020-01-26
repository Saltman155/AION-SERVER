package com.aionstar.commons.network.codec;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


@ChannelHandler.Sharable
public class InnerPacketEncoder extends MessageToByteEncoder<ServerPacket> {

    /**
     * 将数据包转成二进制写出
     * 这个编码器用于在内部服务器两端通信时使用，没有做加密处理
     * @param ctx           上下文
     * @param packet        数据包
     * @param out           输出流
     * @throws Exception    随意抛出
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