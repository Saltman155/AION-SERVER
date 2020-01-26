package com.aionstar.login.network.crypt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 全局密钥生成器
 */
public class LKeyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(LKeyGenerator.class);

    /**初始的blowfish密钥*/
    public static final byte[] BLOWFISH_INIT_KEY = {
            (byte) 0x6b, (byte) 0x60, (byte) 0xcb, (byte) 0x5b, (byte) 0x82,
            (byte) 0xce, (byte) 0x90, (byte) 0xb1, (byte) 0xcc, (byte) 0x2b,
            (byte) 0x6c, (byte) 0x55, (byte) 0x6c, (byte) 0x6c, (byte) 0x6c,
            (byte) 0x6c
    };

    /**加密通讯数据包的Blowfish密钥生成器*/
    private static KeyGenerator blowfishKeyGenerator;
    /**用于加密账号密码的RSA密钥对*/
    private static EncryptedRSAKeyPair[] encryptedRSAKeyPairs;


    public static void init() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        logger.info("密钥生成器初始化...");
        blowfishKeyGenerator = KeyGenerator.getInstance("blowfish");
        encryptedRSAKeyPairs = new EncryptedRSAKeyPair[10];
        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //1024位
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        rsaKeyPairGenerator.initialize(spec);
        //因为RSA密钥生成非常消耗性能，所以处理方案是预先生成一批留用
        for (int i = 0; i < 10; i++) {
            encryptedRSAKeyPairs[i] = new EncryptedRSAKeyPair(
                    rsaKeyPairGenerator.generateKeyPair());
        }
        logger.info("密钥生成器初始化完毕！");
    }

    /**
     * 获取blowfish密钥
     * @return  blowfish密钥
     */
    public static SecretKey generateBlowfishKey(){
        return blowfishKeyGenerator.generateKey();
    }

    /**
     * 随机获取一个RSA加密所需的密钥对
     * @return  密钥对
     */
    public static EncryptedRSAKeyPair getEncryptedRSAKeyPair(){
        return encryptedRSAKeyPairs[ThreadLocalRandom.current().nextInt(10)];
    }

}
