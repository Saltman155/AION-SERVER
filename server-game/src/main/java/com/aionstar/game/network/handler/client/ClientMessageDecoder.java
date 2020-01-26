package com.aionstar.game.network.handler.client;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.client.ClientPacketFactory;
import com.aionstar.game.network.crypt.ClientCrypt;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 用于解密客户端发来的字节数据 -> 具体的封包对象
 */

public class ClientMessageDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageDecoder.class);

    private final ClientPacketFactory packetHandler;

    public ClientMessageDecoder(ClientPacketFactory handler){
        this.packetHandler = handler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Channel channel = ctx.channel();
        int dataLen = in.readableBytes();
        ByteBuf data = channel.attr(ClientChannelAttr.BUFFER).get();
        //先把数据取出来放到临时空间里
        in.readBytes(data,0,dataLen);
        data.readerIndex(0).writerIndex(dataLen);

        //获取连接关联的加&解密器，然后一通解密
        ClientCrypt crypt = channel.attr(ClientChannelAttr.CRYPT).get();
        if(!crypt.decrypt(data)){
            logger.warn("客户端发送的封包解密失败！");
            ctx.channel().close();
            return;
        }
        AionClientPacket packet = packetHandler.handle(data,channel);
        if(packet != null){
            logger.info("收到客户端发来的数据包，code为 {}",packet.getOpcode());
            out.add(packet);
        }else {
            logger.warn("收到无效的客户端数据包！");
        }
    }

}
