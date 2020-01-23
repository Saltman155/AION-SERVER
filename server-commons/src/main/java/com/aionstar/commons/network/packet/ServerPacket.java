package com.aionstar.commons.network.packet;

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

    public int getOpcode() {
        return opcode;
    }

    /**
     * 将数据包内容写入到指定buffer中
     *      服务端封包格式：
     *           包头：    2个字节  2+包体长度
     *           包体：    n个字节
     * @param buf       指定buffer
     */
    public void writeData(ByteBuf buf){
        buf.clear();
        buf.writerIndex(2);
        buf.writeByte(opcode);
        appendBody(buf);
        //头两个字节写入长度
        buf.setShortLE(0,buf.writerIndex());
    }

    /**
     * 向buf中追加数据包具体数据
     * @param buf 数据包buf
     * return     追加的数据长度
     */
    protected abstract void appendBody(ByteBuf buf);

}
