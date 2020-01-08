package com.saltman155.aion.commons.network.packet;

import io.netty.buffer.ByteBuf;

/**
 * 发送的数据包基类
 * @author saltman155
 * @date 2019/10/16 23:58
 */

public abstract class ServerPacket {

    /**数据包操作符*/
    protected final byte opcode;

    public ServerPacket(byte opcode) {
        this.opcode = opcode;
    }

    public byte getOpcode() {
        return opcode;
    }

    /**
     * 写入数据内容
     */
    public void writeData(ByteBuf buf){
        buf.writerIndex(2);
        buf.writeByte(opcode);
        appendBody(buf);
    }

    /**
     * 向buf中追加数据包具体数据
     * @param buf 数据包buf
     */
    protected abstract void appendBody(ByteBuf buf);

}
