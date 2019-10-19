package com.superywd.aion.login.network.crypt;

import com.sun.crypto.provider.BlowfishCipher;
import io.netty.util.AttributeKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 用于加/解密数据包，以及数据校验的工作类
 * @author: saltman155
 * @date: 2019/3/24 16:58
 */

public class XORCheckUtil {

    private XORCheckUtil() { }

    /**
     * 解密获取到的数据
     * @param data          二进制数据数组
     * @param offset        数据起点
     * @param length        数据长度
     * @return              是否加密成功
     */
    public boolean decrypt(byte[] data, int offset, int length) {
        return false;
    }

    /**
     * 为初始数据增加异或校验
     * @param data          需要添加校验的数据域
     * @param offset        数据起点
     * @param length        数据长度
     * @param key           校验key
     */
    public static void encXORPass(byte[] data, int offset, int length, int key) {
        int stop = length - 8;
        int pos = 4 + offset;
        int edx;
        int ecx = key;
        while (pos < stop) {
            edx = (data[pos] & 0xFF);
            edx |= (data[pos + 1] & 0xFF) << 8;
            edx |= (data[pos + 2] & 0xFF) << 16;
            edx |= (data[pos + 3] & 0xFF) << 24;
            ecx += edx;
            edx ^= ecx;
            data[pos++] = (byte) (edx & 0xFF);
            data[pos++] = (byte) (edx >> 8 & 0xFF);
            data[pos++] = (byte) (edx >> 16 & 0xFF);
            data[pos++] = (byte) (edx >> 24 & 0xFF);
        }
        data[pos++] = (byte) (ecx & 0xFF);
        data[pos++] = (byte) (ecx >> 8 & 0xFF);
        data[pos++] = (byte) (ecx >> 16 & 0xFF);
        data[pos] = (byte) (ecx >> 24 & 0xFF);
    }


    /**
     * 为后续数据添加异或校验
     * @param data           需要添加校验的数据域
     * @param offset         数据起点
     * @param length         数据长度
     */
    public static void appendChecksum(byte[] data, int offset, int length) {
        long chksum = 0;
        int count = length - 4;
        long ecx;
        int i;
        for (i = offset; i < count; i += 4) {
            ecx = data[i] & 0xff;
            ecx |= data[i + 1] << 8 & 0xff00;
            ecx |= data[i + 2] << 0x10 & 0xff0000;
            ecx |= data[i + 3] << 0x18 & 0xff000000;
            chksum ^= ecx;
        }
        ecx = data[i] & 0xff;
        ecx |= data[i + 1] << 8 & 0xff00;
        ecx |= data[i + 2] << 0x10 & 0xff0000;
        ecx |= data[i + 3] << 0x18 & 0xff000000;
        data[i] = (byte) (chksum & 0xff);
        data[i + 1] = (byte) (chksum >> 0x08 & 0xff);
        data[i + 2] = (byte) (chksum >> 0x10 & 0xff);
        data[i + 3] = (byte) (chksum >> 0x18 & 0xff);
    }


}
