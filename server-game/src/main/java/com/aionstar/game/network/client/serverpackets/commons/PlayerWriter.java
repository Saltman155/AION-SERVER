package com.aionstar.game.network.client.serverpackets.commons;

import com.aionstar.game.model.account.AccountPlayerData;
import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;

/**
 * 这个类提取了对角色信息写出的格式的方法
 */
public abstract class PlayerWriter extends AionServerPacket {

    public PlayerWriter(byte opcode) {
        super(opcode);
    }

    /**
     * 向封包中写入这个角色的信息
     * @param playerData            角色信息
     */
    protected void writePlayerInfo(ByteBuf buffer,AccountPlayerData playerData){

    }

}
