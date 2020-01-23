package com.aionstar.login.network.mainserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.commons.network.model.BannedMacEntry;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.time.ZoneId;
import java.util.Map;

/**
 * 登录服务器发送给游戏服务端关于封禁mac的名单封包
 * @author saltman155
 * @date 2020/1/18 22:20
 */

public class SM_BAN_MAC_LIST extends ServerPacket {

    private static final byte OPCODE = 0x09;

    private Map<String, BannedMacEntry> bannedList;

    public SM_BAN_MAC_LIST(Map<String, BannedMacEntry> bannedList) {
        super(OPCODE);
        this.bannedList = bannedList;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(bannedList.size());
        //把封禁的mac地址以及封禁到期时间、封禁说明都写进去
        for(BannedMacEntry item : bannedList.values()){
            byte[] mac = item.getMac().getBytes(CharsetUtil.UTF_8);
            byte[] details = item.getDetails().getBytes(CharsetUtil.UTF_8);
            long time = item.getTimeEnd().atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
            buf.writeByte(mac.length);
            buf.writeBytes(mac);
            buf.writeLongLE(time);
            buf.writeShortLE(details.length);
            buf.writeBytes(details);
        }
    }
}
