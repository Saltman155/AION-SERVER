package com.superywd.aion.login.network.aion;

import com.superywd.aion.commons.network.packet.BaseServerPacket;

import java.nio.ByteBuffer;

/**
 * 从登录服务端发往游戏客户端的基数据包
 * @author: saltman155
 * @date: 2019/3/28 19:54
 */
public abstract class ClientPacket extends BaseServerPacket {

    public ClientPacket(int opcode) {
        super(opcode);
    }

    /**
     * 一个数据包的固定格式为：
     *      2个字节：0x00 0x00   用于描述数据包的长度，值为数据包数据长度+2（自身占两个长度）
     *      n个字节：0x?? ...    数据包数据
     *
     *      数据包数据怎么来：
     *              1个字节  0x??      数据包类型，如CM_INIT数据包就是0x00
     *              n个字节  0x?? ...  包中的真实数据（每种数据包都不一样）
     *      将上面两段数据拼在一起，然后做Blowfish加密（第一次通讯时，用和客户端协定好的密钥，后面的通讯用第一次通讯发送的数据包中指定的密钥）
     *      得到的加密结果就是最后的数据包数据，附加在开头的两个描述长度的字节后面
     * @param connection      连接
     */
    public final void write(LoginConnection connection){
        buffer.putShort((short) 0);
        buffer.put((byte)getOpcode());
        writePackData(connection);
        //这里反转再插入两个空字节的操作，是为了把position指针先移到头部再腾两个字节位置
        buffer.flip();
        buffer.putShort((short)0);
        //拿到已经写好的数据包的视图，对视图加密操作
        ByteBuffer view = buffer.slice();
        //加上两个描述长度的字节，就是最后的数据长度字节
        short size = (short) (connection.encrypt(view) + 2);
        //然后放到头部
        buffer.putShort(0,size);

    }


    /**
     * 写入实际数据包数据（由各个实际的子类数据包实现）
     * @param connection    待发送数据的连接
     */
    protected abstract void writePackData(LoginConnection connection);

}
