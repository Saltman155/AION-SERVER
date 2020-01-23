package com.aionstar.login.network.client.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.network.client.LoginAuthResponse;
import io.netty.buffer.ByteBuf;

/**
 * 该数据包，是客户端账户已经登录的状态下，但游戏服务器产生异常的响应封包
 * @author saltman155
 * @date 2020/1/24 0:20
 */

public class SM_PLAY_FAIL extends ServerPacket {

    private static final byte OPCODE = 0x06;

    private LoginAuthResponse response;

    public SM_PLAY_FAIL(LoginAuthResponse response) {
        super(OPCODE);
        this.response = response;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(response.getId());
    }
}
