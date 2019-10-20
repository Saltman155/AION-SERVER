package com.superywd.aion.login.network.aion.serverpackets;

import com.superywd.aion.login.network.aion.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 这个是登录成功，服务端给客户端返回的数据包
 * @author saltman155
 * @date 2019/10/20 21:23
 */

public class SM_LOGIN_OK extends ServerPacket {

    private static final byte OPCODE = 0x03;

    public SM_LOGIN_OK(byte opcode) {
        super(opcode);
    }

    @Override
    protected void appendBody(ByteBuffer buf) {

    }
}
