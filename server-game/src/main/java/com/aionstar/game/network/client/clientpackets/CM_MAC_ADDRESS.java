package com.aionstar.game.network.client.clientpackets;

import com.aionstar.commons.utils.ChannelUtil;
import com.aionstar.game.network.BannedMacManager;
import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * 这个是硬件地址数据包
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
        // 跳过8个字节
        // TODO 这里非常迷...我不知道这个具体长度是多少，我试了半天，感觉应该是需要跳过8个字节
        data.readBytes(8);
        macAddress = ChannelUtil.bufReadS(data);
    }

}
