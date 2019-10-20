package com.superywd.aion.login.network.aion.serverpackets;

import com.superywd.aion.login.network.aion.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 该数据包，是对客户端对服务端的密钥验证的响应包 {@link com.superywd.aion.login.network.aion.clientpackets.CM_AUTH_GG }的响应
 * @author saltman155
 * @date 2019/10/20 18:16
 */

public class SM_AUTH_GG extends ServerPacket {

    private static final byte OPCODE = 0x0b;

    private final int sessionId;

    public SM_AUTH_GG(int sessionId) {
        super(OPCODE);
        this.sessionId = sessionId;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        buf.putInt(sessionId);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.put((byte)0x13);
    }
}
