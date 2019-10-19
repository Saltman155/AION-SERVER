package com.superywd.aion.login.network.aion.serverpackets;

import com.superywd.aion.login.network.aion.ServerPacket;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import io.netty.buffer.ByteBuf;

import javax.crypto.SecretKey;
import java.nio.ByteBuffer;

/**
 *
 * @author saltman155
 * @date 2019/10/17 0:33
 */

public class SM_INIT extends ServerPacket {

    private static final byte OPCODE = 0X00;

    private final int sessionId;


    public SM_INIT(SecretKey blowfishKey, EncryptedRSAKeyPair rsaKeyPair, int sessionId) {
        super(OPCODE, blowfishKey, rsaKeyPair);
        this.sessionId = sessionId;
    }

    /**
     * 写入数据包数据
     * 初始化数据包的数据格式：
     *      4个字节的sessionId       0x?? 0x?? 0x?? 0x??
     *      4个字节的协议id          0x00 0x00 0xc6 0x21
     *      n个字节的rsa算法公钥      0x?? ...
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      4个空字节                0x00 0x00 0x00 0x00
     *      n个字节的blowfish密钥    0x?? ...
     *      一个神奇的数字           0x00 0x03 0x04 0x03
     *      又一个神奇数字           0x00 0x20 0x00 0x00
     * @param buf                   待写入空间
     */
    @Override
    protected void appendBody(ByteBuffer buf){
        buf.putInt(sessionId);
        //序列版本号
        buf.putInt(0X0000C621);
        //RSA公钥
        buf.put(rsaPublicKey.getEncryptedModulus());
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        //blowfish密钥
        buf.put(blowfishKey.getEncoded());
        //两个神秘数字
        buf.putInt(0x00030403);
        buf.putInt(0x00200000);
    }

}
