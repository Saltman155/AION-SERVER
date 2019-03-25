package com.superywd.aion.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Arrays;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/25 20:18
 */
public class Blowfish {

    public static void main(String[] args)throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        byte[] key = keyGenerator.generateKey().getEncoded();
        System.out.println(Arrays.toString(key));
        Cipher c1 = Cipher.getInstance("Blowfish/CBC/NoPadding");


    }
}
