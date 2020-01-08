package com.saltman155.aion.commons.network;

import io.netty.util.AttributeKey;

import java.nio.ByteBuffer;

/**
 * @author saltman155
 * @date 2020/1/5 17:54
 */

public class BaseChannelAttr {


    /**连接读入缓冲区*/
    public static final AttributeKey<ByteBuffer> C_READ_TMP = AttributeKey.newInstance("cReadTmp");
    /**连接写出缓冲区*/
    public final AttributeKey<ByteBuffer> C_WRITE_TMP = AttributeKey.newInstance("cWriteTmp");
}
