package com.aionstar.game.network.client;

import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 客户端发送给主服务端的封包
 * 与 ${@link ClientPacket} 没有区别，唯一的区别就是 opcode 更长了，为short类型的
 * @author saltman155
 * @date 2020/1/24 0:55
 */

public abstract class AionClientPacket extends ClientPacket {

    private final short opcode;

    protected AionClientPacket(short opcode, Channel channel, ByteBuf data) {
        super((byte) 0xFF, channel, data);
        this.opcode = opcode;
    }

    @Override
    public int getOpcode() {
        return opcode;
    }

}
