package com.aionstar.game.network.client.clientpackets;

import com.aionstar.commons.utils.ChannelUtil;
import com.aionstar.game.network.BannedMacManager;
import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * 这个是客户端发来的硬件地址数据包
 * 不是网卡的地址，应该是客户端经过了一些计算转换得到的。
 */

public class CM_MAC_ADDRESS extends AionClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_MAC_ADDRESS.class);

    private String macAddress;

    public CM_MAC_ADDRESS(byte opcode) {
        super(opcode);
    }

    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.CONNECTED);
        validState.add(ClientChannelAttr.SessionState.AUTHED);
        validState.add(ClientChannelAttr.SessionState.ONLINE);
    }

    @Override
    protected void handler() {
        logger.info("MAC ADDRESS: {}",macAddress);
        //判断一下是不是禁硬件了
        if(BannedMacManager.getInstance().isBanned(macAddress)){
            logger.warn("硬件地址 {} 在禁止登录列表中！禁止该账户登录.",macAddress);
            channel.close();
        }else{
            //设置连接的硬件地址
            channel.attr(ClientChannelAttr.MAC_ADDRESS).set(macAddress);
        }

    }

    @Override
    protected void readData() {
        // 跳过7个字节
        // 注：我把这7个字节debug看了一下，好像是 '127' '.' '0' '.' '0' '.' '1'
        // 可能是用户的ip还是什么东西，不过感觉不太重要，有后面的硬件地址就行了
        data.readBytes(7);
        macAddress = ChannelUtil.bufReadS(data);
    }

}
