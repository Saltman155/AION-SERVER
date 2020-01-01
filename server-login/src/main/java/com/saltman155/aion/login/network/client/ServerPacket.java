package com.saltman155.aion.login.network.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 发给客户端的数据包
 * @author saltman155
 * @date 2019/10/16 23:58
 */

public abstract class ServerPacket {

    private static final Logger logger = LoggerFactory.getLogger(ServerPacket.class);

    /**最大服务端数据包*/
    public static final int MAX_PACKET_SIZE = 8192 * 2;

    /**数据包操作符*/
    protected final byte opcode;

    public ServerPacket(byte opcode) {
        this.opcode = opcode;
    }

    public byte getOpcode() {
        return opcode;
    }

    /**
     * 获取数据包内容
     * @return  数据包内容
     */
    public ByteBuffer getData(){
        ByteBuffer data = ByteBuffer.allocate(MAX_PACKET_SIZE);
        data.order(ByteOrder.LITTLE_ENDIAN);
        //跳过头部两个字节（跳过的两个字节将用于稍后写入封包长度）
        data.position(2);
        data.put(opcode);
        appendBody(data);
        return data;
    }

    /**
     * 向buf中追加数据包具体数据
     * @param buf 数据包buf
     */
    protected abstract void appendBody(ByteBuffer buf);

}
