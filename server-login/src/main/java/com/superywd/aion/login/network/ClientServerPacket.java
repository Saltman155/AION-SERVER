package com.superywd.aion.login.network;

import com.superywd.aion.commons.network.packet.BaseServerPacket;
import com.superywd.aion.login.network.aion.LoginConnection;

/**
 * 登录服务器发至客户端的基数据包
 * @author: 迷宫的中心
 * @date: 2019/3/26 22:02
 */

public abstract class ClientServerPacket extends BaseServerPacket {


    public ClientServerPacket(int opcode) {
        super(opcode);
    }

    /**
     * 向连接中写入数据
     * @param connection
     */
    public final void Write(LoginConnection connection){
        buffer.putShort((short)0);
        buffer.put((byte)getOpcode());
        writeData(connection);
        buffer.flip();
        buffer.putShort((short)0);

    }


    /**
     * 写入具体的数据，由各类子类数据包实现
     * @param connection 待写入数据的连接
     */
    public abstract void writeData(LoginConnection connection);
}
