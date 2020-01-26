package com.aionstar.game.network.loginserver.clientpackets;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.game.network.loginserver.LSManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

/**
 * 该封包是登录服务器发来验证登录session的结果
 */
public class CM_ACCOUNT_AUTH_RPS extends ClientPacket {

    private int accountId;

    private boolean result;

    private String accountName;

    private long accumulatedOnlineTime;

    private long accumulatedRestTime;

    private byte accessLevel;

    private byte membership;

    private long toll;

    protected CM_ACCOUNT_AUTH_RPS(byte opcode, Channel channel, ByteBuf data) {
        super(opcode, channel, data);
    }

    @Override
    protected void handler() {
        LSManager.getInstance().clientResponseOfLoginServerAuthKey();
    }

    @Override
    protected void readData() {
        accountId = data.readIntLE();
        result = data.readByte() == 0x01;
        if(result){
            int len = data.readByte() & 0xFF;
            byte[] name = new byte[len];
            data.readBytes(name,0,len);
            accountName = new String(name, CharsetUtil.UTF_8);
            accumulatedOnlineTime = data.readLongLE();
            accumulatedRestTime = data.readLongLE();
            accessLevel = data.readByte();
            membership = data.readByte();
            toll = data.readLongLE();
        }

    }
}
