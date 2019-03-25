package com.superywd.aion.commons.network.packet;

import java.nio.ByteBuffer;

/**
 * 服务端发出的基数据包
 * @author: 迷宫的中心
 * @date: 2019/3/25 22:28
 */

public abstract class BaseServerPacket extends BasePacket {

    /**包含此数据包数据的buffer*/
    public ByteBuffer buffer;

    public BaseServerPacket() {
        super(PacketType.SERVER);
    }

    protected BaseServerPacket(int opcode) {
        super(PacketType.SERVER,opcode);
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     * 向buffer中追加写入一个byte
     * @param value     写入的值
     */
    protected final void writeByte(byte value) {
        buffer.put(value);
    }

    /**
     * 向buffer中追加写入一个short
     * @param value     写入的值
     */
    protected final void writeShort(short value){
        buffer.putShort(value);
    }

    /**
     * 向buffer中追加写入一个int
     * @param value     写入的值
     */
    protected final void writeInt(int value){
        buffer.putInt(value);
    }

    /**
     * 向buffer中追加写入一个long
     * @param value     写入的值
     */
    protected final void writeLong(long value){
        buffer.putFloat(value);
    }

    /**
     * 向buffer中追加写入一个float
     * @param value     写入的值
     */
    protected final void writeFloat(float value){
        buffer.putFloat(value);
    }

    /**
     * 向buffer中追加写入一个double
     * @param value     写入的值
     */
    protected final void writeDouble(double value){
        buffer.putDouble(value);
    }

    /**
     * 向buffer中追加写入一个字符串
     *      注意：写入后要追加一个空字符代表结束
     * @param text      写入的字符串
     */
    protected final void writeString(String text){
        if(text == null){
            buffer.putChar('\000');
        }else{
            final int len = text.length();
            for (int i = 0; i < len; i++) {
                buffer.putChar(text.charAt(i));
            }
            buffer.putChar('\000');
        }
    }

    /**
     * 向buffer中写入一个字节数组
     * @param data      写入的字节数组
     */
    protected final void writeByteArray(byte[] data){
        buffer.put(data);
    }
}
