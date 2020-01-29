package com.aionstar.game.network.client.serverpackets;

import com.aionstar.commons.utils.ChannelUtil;
import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;

public class SM_L2AUTH_LOGIN_CHECK extends AionServerPacket {

    private static final byte OPCODE = (byte) 0xE5;

    private final boolean ok;

    private final String accountName;

    public SM_L2AUTH_LOGIN_CHECK(boolean ok,String accountName) {
        super(OPCODE);
        this.ok = ok;
        this.accountName = accountName;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        //这里我佛了，ok是0，不ok是1，也是个坑
        buf.writeIntLE(ok ? 0x00000000 : 0x00000001);
        ChannelUtil.bufWriteS(buf,accountName);
    }

}
