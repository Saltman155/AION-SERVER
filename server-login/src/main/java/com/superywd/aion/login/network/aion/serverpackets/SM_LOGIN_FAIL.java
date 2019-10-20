package com.superywd.aion.login.network.aion.serverpackets;

import com.superywd.aion.login.network.aion.LoginAuthResponse;
import com.superywd.aion.login.network.aion.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 这个数据封包用来表示服务端向客户端传递登录时产生的一种错误的状态。
 * @author saltman155
 * @date 2019/10/20 18:30
 */

public class SM_LOGIN_FAIL extends ServerPacket {

    private static final byte OPCODE = 0x0b;

    private LoginAuthResponse response;

    public SM_LOGIN_FAIL(LoginAuthResponse response) {
        super(OPCODE);
        this.response = response;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        buf.putInt(response.getId());
    }

}
