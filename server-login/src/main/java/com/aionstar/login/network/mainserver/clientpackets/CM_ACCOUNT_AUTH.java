package com.aionstar.login.network.mainserver.clientpackets;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.controller.AccountController;
import com.aionstar.login.network.client.ClientChannelAttr;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 这个封包是主服务器发来询问某个账号的登录key是不是对的
 */

public class CM_ACCOUNT_AUTH extends ClientPacket {

    private static final byte OPCODE = 0x01;

    private ClientChannelAttr.SessionKey sessionKey;

    public CM_ACCOUNT_AUTH(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        //拿去验证一番
        AccountController.checkAuth(sessionKey,channel);
    }

    @Override
    protected void readData() {
        //把session数据读出来
        int accountId = data.readIntLE();
        int loginSession = data.readIntLE();
        int playSession1 = data.readIntLE();
        int playSession2 = data.readIntLE();
        sessionKey = new ClientChannelAttr.SessionKey(accountId,loginSession,playSession1,playSession2);
    }

}
