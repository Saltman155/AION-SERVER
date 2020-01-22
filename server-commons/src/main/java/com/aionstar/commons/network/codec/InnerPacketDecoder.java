package com.aionstar.commons.network.codec;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 各个游戏服务内部通讯时的数据包解包类
 * @author saltman155
 * @date 2020/1/18 17:44
 */

public class InnerPacketDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(InnerPacketDecoder.class);

    private final BasePacketFactory packetHandler;

    public InnerPacketDecoder(BasePacketFactory packetHandler) {
        this.packetHandler = packetHandler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Channel channel = ctx.channel();
        int dataLen = in.readableBytes();
        ByteBuf data = channel.attr(BaseChannelAttr.BUFFER).get();
        in.readBytes(data,0,dataLen);
        data.readerIndex(0).writerIndex(dataLen);
        ClientPacket packet = packetHandler.handle(data,channel);
        if(packet != null){
            logger.debug("收到数据包，code: {}",packet.getOpcode());
            out.add(packet);
        }else{
            logger.warn("收到无效数据包...");
        }
    }
}
