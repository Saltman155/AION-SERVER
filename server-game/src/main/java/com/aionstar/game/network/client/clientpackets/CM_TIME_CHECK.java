package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.client.serverpackets.SM_TIME_CHECK;

import java.util.HashSet;

/**
 * 我不太清楚这个包是干啥的，也许是客户端发送的心跳包
 */

public class CM_TIME_CHECK extends AionClientPacket {

    private int time;

    public CM_TIME_CHECK(byte opcode) {
        super(opcode);
    }


    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.CONNECTED);
        validState.add(ClientChannelAttr.SessionState.AUTHED);
        validState.add(ClientChannelAttr.SessionState.ONLINE);
    }

    @Override
    protected void handler() {
        channel.writeAndFlush(new SM_TIME_CHECK(time));
    }

    @Override
    protected void readData() {
        time = data.readIntLE();
    }
}
