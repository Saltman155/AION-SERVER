package com.aionstar.login.network.mainserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * 这个封包表示登录服务器向游戏服务器询问某个账号的相关信息
 * @author saltman155
 * @date 2019/11/16 19:48
 */

public class SM_CHARACTER extends ServerPacket {

    private static final byte OPCODE = 0x08;

    private final int accountId;

    public SM_CHARACTER(int accountId) {
        super(OPCODE);
        this.accountId = accountId;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(accountId);
    }

}
