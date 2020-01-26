package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * 这个封包是客户端在推出后，会发给服务端的一个结束封包
 */
public class CM_DISCONNECT extends AionClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_DISCONNECT.class);

    public CM_DISCONNECT(byte opcode) {
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
        logger.info("客户端断开连接...");
    }

    @Override
    protected void readData() {

    }
}
