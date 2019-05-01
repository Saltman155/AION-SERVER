package com.superywd.aion.login.network.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Random;

/**
 * 秘钥生成服务
 *        为Blowfish或RSA加密算法生成密钥对
 * @author: saltman155
 * @date: 2019/3/24 15:43
 */

public class KeyService {

    protected static final Logger logger = LoggerFactory.getLogger(KeyService.class);

    protected static final int LEN = 10;

    private static KeyGenerator blowfishKeyGenerator;

    private static EncryptedRSAKeyPair[] encryptedRSAKeyPairs;



    public static void init() throws GeneralSecurityException {
        logger.info("通讯秘钥生成服务初始化...");
        blowfishKeyGenerator = KeyGenerator.getInstance("Blowfish");
        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //设置RSA秘钥长度为1024位 公用指数 65537
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        rsaKeyPairGenerator.initialize(spec);
        encryptedRSAKeyPairs = new EncryptedRSAKeyPair[LEN];
        for(int i = 0;i < LEN; i++){
            new EncryptedRSAKeyPair(rsaKeyPairGenerator.generateKeyPair());
        }
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/nopadding");
        //初始化加解密器
        rsaCipher.init(Cipher.DECRYPT_MODE, encryptedRSAKeyPairs[0].getRsaKeyPair().getPrivate());
    }


    /**
     * 生成一个Blowfish算法的秘钥
     * @return      秘钥
     */
    public static SecretKey generateBlowfishKey() {
        return blowfishKeyGenerator.generateKey();
    }

    /**
     * 随机获取一个RSA算法的密钥对
     * @return
     */
    public static EncryptedRSAKeyPair getEncryptedRSAKeyPair() {
        //TODO： 原版的服务端自己实现了一个随机数生成器，我这里先用JDK自带的顶一下
        return encryptedRSAKeyPairs[new Random().nextInt(10)];
    }

}
