package com.aionstar.game.network.loginserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * 这个封包是在主服务端接受到客户端发来的${@link com.aionstar.game.network.client.clientpackets.CM_L2AUTH_LOGIN_CHECK}
 * 封包后，解析出相关的登录key，然后通过这个封包向登录服务端验证是否有效
 */

public class SM_ACCOUNT_AUTH extends ServerPacket {

    private static final byte OPCODE = 0x01;

    private int accountId;

    private int loginSession;

    private int playSession1;

    private int playSession2;

    public SM_ACCOUNT_AUTH(int accountId,int loginSession,int playSession1,int playSession2) {
        super(OPCODE);
        this.accountId = accountId;
        this.loginSession = loginSession;
        this.playSession1 = playSession1;
        this.playSession2 = playSession2;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(accountId);
        buf.writeIntLE(loginSession);
        buf.writeIntLE(playSession1);
        buf.writeIntLE(playSession2);
    }

}
