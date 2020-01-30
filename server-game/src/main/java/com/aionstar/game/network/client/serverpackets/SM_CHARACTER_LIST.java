package com.aionstar.game.network.client.serverpackets;

import com.aionstar.game.model.account.Account;
import com.aionstar.game.model.account.AccountPlayerData;
import com.aionstar.game.network.client.serverpackets.commons.PlayerWriter;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class SM_CHARACTER_LIST extends PlayerWriter {

    private static final byte OPCODE = (byte) 0xE4;

    private int playSession2;

    private Account account;

    public SM_CHARACTER_LIST(Account account, int playSession2) {
        super(OPCODE);
        this.account = account;
        this.playSession2 = playSession2;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(playSession2);
        Map<Integer, AccountPlayerData> playerDataMap = account.getPlayers();
        buf.writeByte(playerDataMap.size());
        for(AccountPlayerData data : playerDataMap.values()){
            //写入角色信息
            writePlayerInfo(buf,data);
            //TODO 这里后面的数据是有特殊含义的，al3.9的源码里面有提到这些数据结构，待后续补充
            buf.writeBytes(new byte[14]);

        }
    }
}
