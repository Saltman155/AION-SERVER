package com.aionstar.commons.network;

import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeKey;

import java.nio.ByteBuffer;

/**
 * @author saltman155
 * @date 2020/1/5 17:54
 */

public class BaseChannelAttr {

    /** 读&写 缓冲区*/
    public static final AttributeKey<ByteBuf> BUFFER = AttributeKey.newInstance("buffer");

}
