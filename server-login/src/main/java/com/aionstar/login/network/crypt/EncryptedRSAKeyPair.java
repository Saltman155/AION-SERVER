package com.aionstar.login.network.crypt;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * 这个类的作用是对标准的RSA密钥对进行一个简单的干扰混淆（增强安全性）
 * 具体的混淆操作在方法 encryptModulus 中实现
 * 混淆后的公钥适合加密某些无需解密的数据
 * @author: saltman155
 * @date: 2019/3/22 23:48
 */

public class EncryptedRSAKeyPair {

    /**密钥对*/
    private KeyPair rsaKeyPair;

    /**需要被加密的数据*/
    private byte[] encryptedModulus;

    public EncryptedRSAKeyPair(KeyPair rsaKeyPair){
        this.rsaKeyPair = rsaKeyPair;
        encryptedModulus = encryptModulus(((RSAPublicKey) this.rsaKeyPair.getPublic()).getModulus());
    }

    /**
     * 公钥混淆算法
     * @param modulus
     * @return
     */
    private byte[] encryptModulus(BigInteger modulus) {
        byte[] encryptedModulus = modulus.toByteArray();
        if ((encryptedModulus.length == 0x81) && (encryptedModulus[0] == 0x00)) {
            byte[] temp = new byte[0x80];
            System.arraycopy(encryptedModulus, 1, temp, 0, 0x80);
            encryptedModulus = temp;
        }
        for (int i = 0; i < 4; i++) {
            byte temp = encryptedModulus[i];
            encryptedModulus[i] = encryptedModulus[0x4d + i];
            encryptedModulus[0x4d + i] = temp;
        }
        for (int i = 0; i < 0x40; i++) {
            encryptedModulus[i] = (byte) (encryptedModulus[i] ^ encryptedModulus[0x40 + i]);
        }
        for (int i = 0; i < 4; i++) {
            encryptedModulus[0x0d + i] = (byte) (encryptedModulus[0x0d + i] ^ encryptedModulus[0x34 + i]);
        }
        for (int i = 0; i < 0x40; i++) {
            encryptedModulus[0x40 + i] = (byte) (encryptedModulus[0x40 + i] ^ encryptedModulus[i]);
        }
        return encryptedModulus;
    }


    /**
     * 获取默认的RSA密钥对
     * @return  RSA密钥对
     */
    public KeyPair getRsaKeyPair(){
        return this.rsaKeyPair;
    }

    /**
     * 获取混淆后的公钥
     * @return
     */
    public byte[] getEncryptedModulus(){
        return this.encryptedModulus;
    }


}
