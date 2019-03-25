package com.superywd.aion.crypt;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/25 20:18
 */
public class Blowfish {

    public static void main(String[] args)throws Exception {
        String data = "123";
        String key = "123123123";//密钥长度必须大于8个字节
        try {

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish/CFB/NoPadding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            System.out.println("iv is : " + Arrays.toString(cipher.getIV()));

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data.getBytes());

            System.out.println(Arrays.toString(encryptedData));
            blowfishDecrypt(key, cipher.getIV(), encryptedData);

        } catch (Exception e) {
            System.err.println("出错!");
            e.printStackTrace();
        }

    }

    public static void blowfishDecrypt(String key, byte[] iv, byte[] secretContent) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/CFB/NoPadding");

        IvParameterSpec param = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, param);
        System.out.println("iv is : " + Arrays.toString(cipher.getIV()));

        // 执行解密操作
        byte decryptedData[] = cipher.doFinal(secretContent);
        System.out.println(Arrays.toString(decryptedData));

    }


    @Test
    public void test(){
        Provider[] providers = Security.getProviders();
        for(Provider item : providers){
            System.out.println(item.getInfo());
        }
        Arrays.toString(Security.getProviders());
        System.out.println();
    }
}
