package com.aionstar.login.network.client.serverpackets;

import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.network.client.clientpackets.CM_LOGIN;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * 对客户端 ${@link CM_LOGIN}
 * 封包的响应封包，表示登录成功
 * @author saltman155
 * @date 2019/10/20 21:23
 */

public class SM_LOGIN_OK extends ServerPacket {

    private static final byte OPCODE = 0x03;

    /**登录用户账号ID*/
    private final int accountId;
    /**会话密钥*/
    private final int loginSession;

    public SM_LOGIN_OK(ClientChannelAttr.SessionKey key){
        super(OPCODE);
        this.accountId = key.accountId;
        this.loginSession = key.loginSession;
    }

    @Override
    protected void appendBody(ByteBuf buffer) {
        //写入数据内容（里面又有一些莫名奇妙的数字）
        buffer.writeIntLE(accountId);
        buffer.writeIntLE(loginSession);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x000003ea);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeByte(0x13);
    }
}
