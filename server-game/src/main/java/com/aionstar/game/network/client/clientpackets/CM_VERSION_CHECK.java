package com.aionstar.game.network.client.clientpackets;

import com.aionstar.game.network.client.AionClientPacket;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.client.serverpackets.SM_VERSION_CHECK;

import java.util.HashSet;

/**
 * 这个数据包是客户端对游戏服务端发出的第一个数据包
 * 看名字应该是对服务端版本检查
 */
public class CM_VERSION_CHECK extends AionClientPacket {

    /**版本号1*/
    private int version;
    /**版本号2*/
    private int subversion;

    public CM_VERSION_CHECK(byte opcode) {
        super(opcode);
    }

    @Override
    protected void buildValidState() {
        validState = new HashSet<>();
        validState.add(ClientChannelAttr.SessionState.CONNECTED);
    }

    @Override
    protected void handler() {
        //反手发一个包回去
        channel.writeAndFlush(new SM_VERSION_CHECK(version));
    }

    @Override
    protected void readData() {
        version = data.readIntLE();
        subversion = data.readIntLE();
    }
}
