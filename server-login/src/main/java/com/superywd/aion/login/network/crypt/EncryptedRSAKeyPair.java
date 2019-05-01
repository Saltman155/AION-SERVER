package com.superywd.aion.login.network.crypt;

import java.security.KeyPair;

/**
 * RSA加密公&私对以及加密数据
 * @author: saltman155
 * @date: 2019/3/22 23:48
 */

public class EncryptedRSAKeyPair {

    /**密钥对*/
    private KeyPair rsaKeyPair;

    /**需要被加密的数据*/
    private byte[] encryptModulus;

    public EncryptedRSAKeyPair(KeyPair rsaKeyPair){
            this.rsaKeyPair = rsaKeyPair;
    }

    /**
     * 获取默认的RSA密钥对
     * @return  RSA密钥对
     */
    public KeyPair getRsaKeyPair(){
        return this.rsaKeyPair;
    }

    /**
     * 获取需要被加密的数据
     * @return 数据
     */
    public byte[] getEncryptedModulus(){
        return this.encryptModulus;
    }


}
