package com.aionstar.commons.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

/**
 * @author saltman155
 * @date 2020/1/5 17:09
 */

public class PacketFrameDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger logger = LoggerFactory.getLogger(PacketFrameDecoder.class);

    private static final int LENGTH_FIELD_OFFSET = 0;
    private static final int LENGTH_FIELD_LENGTH = 2;
    private static final int LENGTH_FIELD_ADJUSTMENT = -2;
    private static final int INITIAL_BYTES_TO_STRIP = 2;


    public PacketFrameDecoder(int maxPacketSize) {

        //对于这个自定义封包长度编码器的参数详解：
        //  byteOrder             数据存储模式（大端&小端）
        //  maxFrameLength        接收数据帧的最大长度
        //  lengthFieldOffset     定义长度域位于发送的字节数组中的下标。换句话说：发送的字节数组中下标为${lengthFieldOffset}的地方是长度域的开始地方
        //  lengthFieldLength     用于描述定义的长度域的长度。换句话说：发送字节数组bytes时,  字节数组bytes[lengthFieldOffset, lengthFieldOffset+lengthFieldLength]域对应于的定义长度域部分
        //  lengthAdjustment      一个控制参数，满足公式: 发送的字节数组bytes.length - lengthFieldLength =
        //                        bytes[lengthFieldOffset, lengthFieldOffset+lengthFieldLength] + lengthFieldOffset + lengthAdjustment
        //  initialBytesToStrip   一开始在解析数据包时，需要跳过的位数（即设置是否需要丢弃数据）
        //  failFast              是否一旦读到超过最大长度的数据，就抛出异常，默认为true，建议不要修改，否则可能有内存异常

        //这里我设置lengthAdjustment为-2，是因为客户端的封包里存的长度包含了存长度的空间本身的长度
        super(
                ByteOrder.LITTLE_ENDIAN,
                maxPacketSize,
                LENGTH_FIELD_OFFSET,
                LENGTH_FIELD_LENGTH,
                LENGTH_FIELD_ADJUSTMENT,
                INITIAL_BYTES_TO_STRIP,
                true);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }


}
