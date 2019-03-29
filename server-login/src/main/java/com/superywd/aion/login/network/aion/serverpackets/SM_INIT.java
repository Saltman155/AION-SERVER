package com.superywd.aion.login.network.aion.serverpackets;

import com.superywd.aion.login.network.aion.ClientServerPacket;
import com.superywd.aion.login.network.aion.LoginConnection;

import javax.crypto.SecretKey;

/**
 * 登录服务器初始化通讯数据包
 *      游戏客户端与登录服务端连接发出的第一个数据包，与客户端协定rsa公钥以及blowfish密钥
 * @author: 迷宫的中心
 * @date: 2019/3/28 20:02
 */
public class SM_INIT extends ClientServerPacket {

    /**会话唯一id*/
    private final int sessionId;
    /**rsa加密公钥*/
    private final byte[] publicRsaKey;
    /**blowfish加密密钥*/
    private final byte[] blowfishKey;


    public SM_INIT(LoginConnection connection, SecretKey blowfishKey) {
        this(connection.getEncryptedModulus(), blowfishKey.getEncoded(), connection.getSessionId());
    }

    private SM_INIT(byte[] publicRsaKey, byte[] blowfishKey, int sessionId) {
        //初始数据包的opCode为0x00
        super(0x00);
        this.sessionId = sessionId;
        this.publicRsaKey = publicRsaKey;
        this.blowfishKey = blowfishKey;
    }

    /**
     * 写入数据包数据
     * 初始化数据包的数据格式：
     *      4个字节的sessionId       0x?? 0x?? 0x?? 0x??
     *      4个字节的协议id          0x00 0x00 0xc6 0x21
     *      n个字节的rsa算法公钥     0x?? ...
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      n个字节的blowfish密钥    0x?? ...
     *      一个神奇的数字           0x00 0x03 0x04 0x03
     *      又一个神奇数字           0x00 0x20 0x00 0x00
     * @param connection    待发送数据的连接
     */
    @Override
    protected void writePackData(LoginConnection connection) {
        writeInt(sessionId);
        writeInt(0x0000c621);
        writeByteArray(publicRsaKey);
        writeInt(0x00000000);
        writeInt(0x00000000);
        writeInt(0x00000000);
        writeInt(0x00000000);
        writeByteArray(blowfishKey);
        writeInt(0x00030403);
        writeInt(0x00200000);
    }
}
