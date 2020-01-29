package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;

import java.util.HashSet;

/**
 * 这个封包，是客户端在接到 ${@link com.aionstar.game.network.client.serverpackets.SM_L2AUTH_LOGIN_CHECK}的成功
 * 响应后，发出的获取账号角色的封包
 */

public class CM_CHARACTER_LIST extends AionClientPacket {

    /**验证session*/
    private int playSession2;

    public CM_CHARACTER_LIST(byte opcode) {
        super(opcode);
    }

    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.AUTHED);
    }

    @Override
    protected void handler() {

    }

    @Override
    protected void readData() {
        //获取验证session 但并不去鸟它是不是对的
        playSession2 = data.readIntLE();
    }

}
