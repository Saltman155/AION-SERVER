package com.aionstar.login.network.client.serverpackets;

import com.aionstar.login.network.client.LoginAuthResponse;
import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;


/**
 * 这个数据封包用来表示服务端向客户端响应各种错误的状态。
 * @author saltman155
 * @date 2019/10/20 18:30
 */

public class SM_LOGIN_FAIL extends ServerPacket {

    private static final byte OPCODE = 0x01;

    private LoginAuthResponse response;

    public SM_LOGIN_FAIL(LoginAuthResponse response) {
        super(OPCODE);
        this.response = response;
    }

    @Override
    protected void appendBody(ByteBuf buffer) {
        buffer.writeIntLE(response.getId());
    }

}
