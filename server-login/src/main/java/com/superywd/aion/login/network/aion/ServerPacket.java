package com.superywd.aion.login.network.aion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 聊天服务端发送给客户端的数据包的基本结构
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
     *      数据包结构为：
     *      1个字节  0x??      数据包类型，如CM_INIT数据包就是0x00
     *      n个字节  0x?? ...  包中的真实数据（每种数据包都不一样）
     *      将上面两段数据拼在一起，然后做Blowfish加密（第一次通讯时，用和客户端协定好的密钥，后面的通讯用第一次通讯发送的数据包中商定的密钥）
     *      得到的加密结果就是最后的数据包数据，附加在开头的两个描述长度的字节后面
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

    protected abstract void appendBody(ByteBuffer buf);

}
