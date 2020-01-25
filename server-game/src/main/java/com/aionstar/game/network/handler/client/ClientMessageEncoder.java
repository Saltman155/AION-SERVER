package com.aionstar.game.network.handler.client;


import com.aionstar.game.network.client.AionServerPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.crypt.ClientCrypt;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 用于加密服务端发送给客户端的数据包
 */

@ChannelHandler.Sharable
public class ClientMessageEncoder extends MessageToByteEncoder<AionServerPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AionServerPacket packet, ByteBuf out) throws Exception {
        Channel channel = ctx.channel();
        ByteBuf buffer = channel.attr(ClientChannelAttr.BUFFER).get();
        ClientCrypt crypt = channel.attr(ClientChannelAttr.CRYPT).get();
        //把数据咔咔一通写到临时buffer然后加密
        packet.writeAndEncryptData(buffer,crypt);
        //然后写出去
        out.writeBytes(buffer);
    }
}
