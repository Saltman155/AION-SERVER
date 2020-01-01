package com.saltman155.aion.login.network.client.serverpackets.response;

import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 对客户端 ${@link com.saltman155.aion.login.network.client.clientpackets.request.CM_LOGIN_REQUEST}
 * 封包的响应封包，表示登录成功
 * @author saltman155
 * @date 2019/10/20 21:23
 */

public class SM_LOGIN_OK_RESPONSE extends ServerPacket {

    private static final byte OPCODE = 0x03;

    /**登录用户账号ID*/
    private final int accountId;
    /**会话密钥*/
    private final int loginSession;

    public SM_LOGIN_OK_RESPONSE(ClientChannelAttr.SessionKey key){
        super(OPCODE);
        this.accountId = key.accountId;
        this.loginSession = key.loginSession;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        //写入数据内容（里面又有一些莫名奇妙的数字）
        buf.putInt(accountId);
        buf.putInt(loginSession);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x000003ea);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.put((byte) 0x13);
    }
}
