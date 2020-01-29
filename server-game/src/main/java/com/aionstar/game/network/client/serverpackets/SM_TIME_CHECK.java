package com.aionstar.game.network.client.serverpackets;

import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个数据包是对 ${@link com.aionstar.game.network.client.clientpackets.CM_TIME_CHECK} 的响应
 * 看样子是响应了一个服务端时间回去，我估计就是心跳了
 */

public class SM_TIME_CHECK extends AionServerPacket {

    private static final Logger logger = LoggerFactory.getLogger(SM_TIME_CHECK.class);

    private static final byte OPCODE = 0x45;

    private int clientTime;

    private int serverTime;


    public SM_TIME_CHECK(int clientTime) {
        super(OPCODE);
        this.clientTime = clientTime;
        this.serverTime = (int) System.currentTimeMillis();
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        logger.info("写入时间响应");
        buf.writeIntLE(serverTime);
        buf.writeIntLE(clientTime);
    }
}
