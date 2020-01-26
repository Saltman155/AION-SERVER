package com.aionstar.login.network.mainserver.clientpackets;

import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author saltman155
 * @date 2020/1/18 18:48
 */

public class CM_MAC extends ClientPacket {

    private static final byte OPCODE = 0x00;

    public CM_MAC(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {

    }

    @Override
    protected void readData() {

    }
}
