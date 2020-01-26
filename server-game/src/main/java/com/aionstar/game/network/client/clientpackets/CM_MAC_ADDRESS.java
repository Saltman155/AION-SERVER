package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;

import java.util.HashSet;

public class CM_MAC_ADDRESS extends AionClientPacket {

    private String macAddress;

    protected CM_MAC_ADDRESS(byte opcode) {
        super(opcode);
    }

    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.CONNECTED);
    }

    @Override
    protected void handler() {
    }

    @Override
    protected void readData() {
    }

}
