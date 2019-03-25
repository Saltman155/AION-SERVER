package com.superywd.aion.login.network.crypt;

import com.sun.crypto.provider.BlowfishCipher;

/**
 * 用于加/解密数据包，以及错误处理&校验的加密引擎
 * @author: 迷宫的中心
 * @date: 2019/3/24 16:58
 */

public class CryptEngine {

    /**默认的密钥*/
    private byte[] key = {
            (byte) 0x6b, (byte) 0x60, (byte) 0xcb, (byte) 0x5b, (byte) 0x82,
            (byte) 0xce, (byte) 0x90, (byte) 0xb1, (byte) 0xcc, (byte) 0x2b,
            (byte) 0x6c, (byte) 0x55, (byte) 0x6c, (byte) 0x6c, (byte) 0x6c,
            (byte) 0x6c
    };

    /**密钥是否被更新*/
    private boolean updatedKey = false;

    /**blowfish算法加密器*/
    private BlowfishCipher cipher;


    public CryptEngine() {
//        cipher = new BlowfishCipher(key);
    }


    /**
     * 解密获取到的数据
     * @param data          二进制数据数组
     * @param offset        数据起点
     * @param length        数据长度
     * @return
     */
    public boolean decrypt(byte[] data, int offset, int length) {
        return false;
    }






}
