package com.aionstar.game.network.client.serverpackets;

import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * 服务端发送给客户端的第一个数据包
 * @author saltman155
 * @date 2020/1/24 1:32
 */

public class SM_KEY extends AionServerPacket {

    private static final byte OPCODE = 0x64;

    private int key;

    public SM_KEY(int key) {
        super(OPCODE);
        this.key = key;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(key);
    }
}
