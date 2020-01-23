package com.aionstar.game.network.loginserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * 该数据包是响应客户端发来的封包 ${@link com.aionstar.game.network.loginserver.clientpackets.CM_GS_CHARACTER}
 * 返回指定账号的角色数量
 * @author saltman155
 * @date 2020/1/22 23:00
 */

public class SM_GS_CHARACTER extends ServerPacket {

    private static final byte OPCODE = 0x08;

    private int accountId;

    private int count;

    public SM_GS_CHARACTER(int accountId,int count) {
        super(OPCODE);
        this.accountId = accountId;
        this.count = count;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(accountId);
        buf.writeByte(count);
    }
}
