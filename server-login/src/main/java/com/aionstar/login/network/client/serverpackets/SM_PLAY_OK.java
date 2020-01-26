package com.aionstar.login.network.client.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.network.client.ClientChannelAttr;
import io.netty.buffer.ByteBuf;

/**
 * 表示登录成功的封包
 * 该封包表示用户登录游戏主服务器成功，并且传递session1与session2两个校验密钥
 * @author saltman155
 * @date 2020/1/24 0:28
 */

public class SM_PLAY_OK extends ServerPacket {

    private static final byte OPCODE = 0x07;

    private final int playSession1;

    private final int playSession2;

    private byte serverId;

    public SM_PLAY_OK(byte serverId, ClientChannelAttr.SessionKey sessionKey) {
        super(OPCODE);
        this.serverId = serverId;
        this.playSession1 = sessionKey.playSession1;
        this.playSession2 = sessionKey.playSession2;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(playSession1);
        buf.writeIntLE(playSession2);
        buf.writeByte(serverId);
        buf.writeByte(0x0E);
    }
}
