package com.aionstar.game.network.client.serverpackets;

import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * @author saltman155
 * @date 2020/1/24 1:32
 */

public class SM_KEY extends AionServerPacket {

    public SM_KEY(short opcode) {
        super(opcode);
    }

    @Override
    protected void appendBody(ByteBuf buf) {

    }
}
