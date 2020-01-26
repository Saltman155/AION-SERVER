package com.aionstar.game.network.loginserver.clientpackets;

import com.aionstar.commons.network.model.BannedMacEntry;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.commons.utils.ArrayUtil;
import com.aionstar.game.network.BannedMacManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个数据包是登录服务器发送来的，告知mac封禁的列表
 * @author saltman155
 * @date 2020/1/19 0:48
 */

public class CM_BAN_MAC_LIST extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_BAN_MAC_LIST.class);

    public CM_BAN_MAC_LIST(byte opcode, Channel channel, ByteBuf data) {
        super(opcode, channel, data);
    }

    /**惨遭封禁mac地址的列表*/
    private List<BannedMacEntry> bannedList;

    @Override
    protected void handler() {
        BannedMacManager macManager = BannedMacManager.getInstance();
        //加进去就完事了
        if(!ArrayUtil.isEmpty(bannedList)){
            logger.info("从登录服务器获取了 {} 个mac封禁名单.",bannedList.size());
            for(BannedMacEntry item : bannedList){
                macManager.dbLoad(item);
            }
        }
    }

    @Override
    protected void readData() {
        int len = data.readIntLE(),size;
        bannedList = new ArrayList<>(len);
        for(int i = 0; i<len; i++){
            BannedMacEntry item = new BannedMacEntry();
            size = data.readByte();
            byte[] mac = new byte[size];
            data.readBytes(mac,0,size);
            long time = data.readLongLE();
            size = data.readShortLE();
            byte[] details = new byte[size];
            data.readBytes(details,0,size);
            item.setMac(new String(mac, CharsetUtil.UTF_8));
            //这个时区写死了，感觉不太好
            item.setTimeEnd(LocalDateTime.ofEpochSecond(time,0, ZoneOffset.of("+08:00")));
            item.setDetails(new String(details,CharsetUtil.UTF_8));
            bannedList.add(item);
        }
    }

}
