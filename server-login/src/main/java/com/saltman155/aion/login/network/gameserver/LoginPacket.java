package com.saltman155.aion.login.network.gameserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author saltman155
 * @date 2019/11/16 19:31
 */

public abstract class LoginPacket {

    private static final Logger logger = LoggerFactory.getLogger(LoginPacket.class);

    /**登陆服务端发往游戏服务端数据包最大长度*/
    public static final int MAX_PACKET_SIZE = 8192 * 2;

    /**数据包操作符*/
    protected final byte opcode;

    public LoginPacket(byte opcode) {
        this.opcode = opcode;
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
        //写入操作符
        data.put(opcode);
        appendBody(data);
        return data;
    }

    /**
     * 向buf中追加数据包的具体数据
     * @param buf 数据包buf
     */
    protected abstract void appendBody(ByteBuffer buf);

}
