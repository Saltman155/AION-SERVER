package com.superywd.aion.commons.network.packet;

/**
 * 基数据包
 * @author: 迷宫的中心
 * @date: 2019/3/25 22:12
 */

public abstract class BasePacket {


    /**
     * 默认的数据包字符串表现格式
     */
    public static final String TYPE_PATTERN = "[%s] 0x%02X %s";


    /**数据包类型*/
    public final PacketType packetType;

    /**数据包操作码字段*/
    private int opcode;


    /**
     * 只用包类型来初始化
     * 注意，如果使用这个构造器，一定要在后面设置这个数据包的opcode
     * @param packetType    数据包类型
     */
    protected BasePacket(PacketType packetType) {
        this.packetType = packetType;
    }

    /**
     * 使用包类型以及操作码字段来初始化这个数据包
     * @param packetType    数据包类型
     * @param opcode        操作码字段
     */
    protected BasePacket(PacketType packetType, int opcode) {
        this.packetType = packetType;
        this.opcode = opcode;
    }


    public PacketType getPacketType() {
        return packetType;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    /**
     * 获得数据包的名称
     * @return      数据包名称（实际上是类名）
     */
    public String getPacketName() {
        return this.getClass().getSimpleName();
    }


    @Override
    public String toString() {
        return String.format(TYPE_PATTERN, getPacketType().getName(), getOpcode(), getPacketName());
    }
}
