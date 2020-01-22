package com.aionstar.login.network.mainserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.network.mainserver.MSAuthResponse;
import io.netty.buffer.ByteBuf;

/**
 * 对主服务器的连接验证请求的响应数据包
 * @author saltman155
 * @date 2020/1/18 22:36
 */

public class SM_GS_AUTH_RPS extends ServerPacket {

    private static final byte OPCODE = 0x00;

    private final MSAuthResponse response;

    private final int serverSize;

    public SM_GS_AUTH_RPS(MSAuthResponse response) {
        super(OPCODE);
        this.response = response;
        serverSize = 0;
    }

    public SM_GS_AUTH_RPS(MSAuthResponse response, int serverSize) {
        super(OPCODE);
        this.response = response;
        this.serverSize = serverSize;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeByte(response.getCode());
        if(response.getCode() == 0x00){
            buf.writeByte(serverSize);
        }
    }
}
