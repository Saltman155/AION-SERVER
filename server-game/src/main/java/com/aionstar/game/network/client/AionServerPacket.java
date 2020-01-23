package com.aionstar.game.network.client;

import com.aionstar.commons.network.packet.ServerPacket;

/**
 * 主服务端发送给客户端的封包
 * 与 ${@link ServerPacket } 没有区别，唯一的区别就是 opcode 更长了，为short类型的
 * @author saltman155
 * @date 2020/1/24 1:00
 */

public abstract class AionServerPacket extends ServerPacket {

    /**数据包操作符*/
    private final short opcode;

    public AionServerPacket(short opcode) {
        super((byte) 0xFF);
        this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
        return opcode;
    }


}
