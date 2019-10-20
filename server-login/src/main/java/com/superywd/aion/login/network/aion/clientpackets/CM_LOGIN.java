package com.superywd.aion.login.network.aion.clientpackets;

import com.superywd.aion.login.network.aion.ClientPacket;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 登录
 * @author saltman155
 * @date 2019/10/20 20:01
 */

public class CM_LOGIN extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_LOGIN.class);

    private static final int OPCODE = 0x0b;

    public CM_LOGIN(Channel channel, ByteBuffer data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {

    }

    @Override
    protected void readData() {

    }
}
