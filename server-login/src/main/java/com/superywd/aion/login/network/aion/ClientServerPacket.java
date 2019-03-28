package com.superywd.aion.login.network.aion;

import com.superywd.aion.commons.network.packet.BaseServerPacket;

/**
 * 从登录服务端发往客户端的基数据包
 * @author: 迷宫的中心
 * @date: 2019/3/28 19:54
 */
public abstract class ClientServerPacket extends BaseServerPacket {

    public ClientServerPacket(int opcode) {
        super(opcode);
    }

    /**
     * 一个数据包的固定格式为：
     *      2个字节：0x00 0x00   两个空字节
     *      2个字节：0x?? 0x??   数据包类型code
     *      n个字节：0x?? ...    实际数据包数据
     *      2个字节：0x00 0x00   两个空字节
     *
     * @param connection
     */
    public final void write(LoginConnection connection){
        buffer.putShort((short) 0);
        buffer.put((byte)getOpcode());
        writePackData(connection);
        buffer.flip();

    }


    /**
     * 写入实际数据包数据（由各个实际的子类数据包实现）
     * @param connection    待发送数据的连接
     */
    protected abstract void writePackData(LoginConnection connection);

}
