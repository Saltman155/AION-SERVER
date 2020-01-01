package com.saltman155.aion.login.network.gameserver.loginpackets.request;

import com.saltman155.aion.login.network.gameserver.LoginPacket;

import java.nio.ByteBuffer;

/**
 * 这个封包表示登录服务器向游戏服务器询问某个账号的相关信息
 * @author saltman155
 * @date 2019/11/16 19:48
 */

public class SM_CHARACTER_REQUEST extends LoginPacket {

    private static final byte OPCODE = 0x08;

    private final int accountId;

    public SM_CHARACTER_REQUEST(int accountId) {
        super(OPCODE);
        this.accountId = accountId;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        buf.putInt(accountId);
    }

}
