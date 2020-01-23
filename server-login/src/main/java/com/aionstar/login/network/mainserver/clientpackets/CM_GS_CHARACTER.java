package com.aionstar.login.network.mainserver.clientpackets;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.config.spring.SpringContext;
import com.aionstar.login.controller.AccountController;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 该数据包对应主服务端发送的 ${@link com.aionstar.game.network.loginserver.serverpackets.SM_GS_CHARACTER}
 * @author saltman155
 * @date 2020/1/22 23:44
 */

public class CM_GS_CHARACTER extends ClientPacket {

    private static final byte OPCODE = 0x08;

    private int accountId;

    private int count;

    public CM_GS_CHARACTER(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        MainServerInfo info = channel.attr(MSChannelAttr.SERVER_INFO).get();
        SpringContext.getBean(AccountController.class).
    }

    @Override
    protected void readData() {
        accountId = data.readIntLE();
        count = data.readByte();
    }
}
