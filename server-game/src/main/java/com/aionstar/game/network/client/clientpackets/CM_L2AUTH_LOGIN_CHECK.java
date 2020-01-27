package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.loginserver.LSManager;

import java.util.HashSet;

/**
 * 该数据包是客户端发给主服务端的二级验证封包
 * 它将发送从登录服务端获得的 loginSession、playSession1、playSession2
 */
public class CM_L2AUTH_LOGIN_CHECK extends AionClientPacket {


    private int accountId;

    private int loginSession;

    private int playerSession1;

    private int playerSession2;


    public CM_L2AUTH_LOGIN_CHECK(byte opcode) {
        super(opcode);
    }

    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.CONNECTED);
    }

    @Override
    protected void handler() {
        //一通验证
        LSManager.getInstance().clientRequestOfLoginServerAuthKey(accountId,channel,loginSession,playerSession1,playerSession2);
    }

    @Override
    protected void readData() {
        //把数据一通读取
        playerSession2 = data.readIntLE();
        playerSession1 = data.readIntLE();
        accountId = data.readIntLE();
        loginSession = data.readIntLE();
    }

}
