package com.aionstar.game.network.crypt;

import com.aionstar.game.network.exception.KeyAlreadySetException;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 该类用于处理游戏服务端与客户端的的通讯时的加解密操作
 */
public class ClientCrypt {

    private static final Logger logger = LoggerFactory.getLogger(ClientCrypt.class);

    /**
     * 服务端发送的数据包的第二个字节固定为这个值
     * 0x54 也可以工作，推测这个值代表服务端版本号
     */
    public static final byte STATIC_SERVER_PACKET_CODE = 0x50;

    /** 客户端发送的数据包的第二个字节固定为这个值*/
    public static final byte STATIC_CLIENT_PACKET_CODE = 0x55;

    /** 异或加密使用的key*/
    private static byte[] STATIC_XOR_KEY = "nKO/WctQ0AVLbpzfBkS6NevDYT8ourG5CRlmdjyJ72aswx4EPq1UgZhFMXH?3iI9".getBytes();

    private byte[] clientPacketKey;

    private byte[] serverPacketKey;

    /**
     * 是否已经启用了加解密
     * 注意：第一个数据包是没有启用加解密的
     */
    private volatile boolean isEnabled;


    /**
     * 启用Crypt
     *      生成随机的clientPacketKey与sererPacketKey,并返回生成时的种子
     * @return      种子
     */
    public final int enableKey(){
        if(clientPacketKey != null){
            throw new KeyAlreadySetException();
        }
        //生成种子
        int key = ThreadLocalRandom.current().nextInt();
        //生成客户端key,固定长度8个字节
        clientPacketKey = new byte[] {
                (byte) (key & 0xff),            (byte) ((key >> 8) & 0xff),
                (byte) ((key >> 16) & 0xff),    (byte) ((key >> 24) & 0xff),
                (byte) 0xa1,                    (byte) 0x6c,
                (byte) 0x54,                    (byte) 0x87 };
        serverPacketKey = new byte[clientPacketKey.length];
        //服务端key与客户端key保持一致
        System.arraycopy(clientPacketKey,0,serverPacketKey,0,clientPacketKey.length);

        //这个Key将用于首次服务端与客户端进行通讯使用
        //需要进行混淆
        return (key ^ 0xCD92E451) + 0x3FF2CC87;
    }


    /**
     * 数据包解密
     * @param buf   待解密的数据
     * @return      解密结果
     */
    public final boolean decrypt(ByteBuf buf){
        if(!isEnabled){
            return false;
        }
        final byte[] data = buf.array();
        final int size = buf.readableBytes();
        int index = 0;
        int prev = data[index];
        //解密第一个
        data[index++] ^= (clientPacketKey[0] & 0xff);
        //解密后面的
        for(int i = 1; i < size; i++, index++) {
            int curr = data[index] & 0xff;
            data[index] ^= (STATIC_XOR_KEY[i & 63] & 0xff) ^ (clientPacketKey[i & 7] & 0xff) ^ prev;
            prev = curr;
        }

        long oldKey =
                (((long) clientPacketKey[0] & 0xff)) | (((long) clientPacketKey[1] & 0xff) << 8) |
                (((long) clientPacketKey[2] & 0xff) << 16) | (((long) clientPacketKey[3] & 0xff) << 24) |
                (((long) clientPacketKey[4] & 0xff) << 32) | (((long) clientPacketKey[5] & 0xff) << 40) |
                (((long) clientPacketKey[6] & 0xff) << 48) | (((long) clientPacketKey[7] & 0xff) << 56);
        oldKey += size;

        //更新clientPacketKey
        clientPacketKey[0] = (byte) (oldKey & 0xff);
        clientPacketKey[1] = (byte) (oldKey >> 8 & 0xff);
        clientPacketKey[2] = (byte) (oldKey >> 16 & 0xff);
        clientPacketKey[3] = (byte) (oldKey >> 24 & 0xff);
        clientPacketKey[4] = (byte) (oldKey >> 32 & 0xff);
        clientPacketKey[5] = (byte) (oldKey >> 40 & 0xff);
        clientPacketKey[6] = (byte) (oldKey >> 48 & 0xff);
        clientPacketKey[7] = (byte) (oldKey >> 56 & 0xff);

        //判定一下解密出来的头是不是规定的数据
        return buf.getByte(0) == ~buf.getByte(2) &&
                buf.getByte(1) == STATIC_CLIENT_PACKET_CODE;
    }

    /**
     * 数据包加密
     * @param buf   待加密的数据
     */
    public final void encrypt(ByteBuf buf){
        if(!isEnabled) {
            //首次通讯，不进行加密
            isEnabled = true;
            return;
        }
        buf.readerIndex(2);
        final byte[] data = buf.array();
        final int size = buf.readableBytes();
        int index = buf.readerIndex();
        //加密第一个字节
        data[index] ^= (serverPacketKey[0] & 0xff);
        //获取加密后续数据需要的prev
        int prev = data[index++];
        //加密后面的数据
        for(int i = 1; i < size; i++, index++) {
            data[index] ^= (STATIC_XOR_KEY[i & 63] & 0xff) ^ (serverPacketKey[i & 7] & 0xff) ^ prev;
            prev = data[index];
        }
        long oldKey =
                (((long) serverPacketKey[0] & 0xff)) | (((long) serverPacketKey[1] & 0xff) << 8) |
                (((long) serverPacketKey[2] & 0xff) << 16) | (((long) serverPacketKey[3] & 0xff) << 24) |
                (((long) serverPacketKey[4] & 0xff) << 32) | (((long) serverPacketKey[5] & 0xff) << 40) |
                (((long) serverPacketKey[6] & 0xff) << 48) | (((long) serverPacketKey[7] & 0xff) << 56);
        oldKey += size;
        //更新serverPacketKey
        serverPacketKey[0] = (byte) (oldKey & 0xff);
        serverPacketKey[1] = (byte) (oldKey >> 8 & 0xff);
        serverPacketKey[2] = (byte) (oldKey >> 16 & 0xff);
        serverPacketKey[3] = (byte) (oldKey >> 24 & 0xff);
        serverPacketKey[4] = (byte) (oldKey >> 32 & 0xff);
        serverPacketKey[5] = (byte) (oldKey >> 40 & 0xff);
        serverPacketKey[6] = (byte) (oldKey >> 48 & 0xff);
        serverPacketKey[7] = (byte) (oldKey >> 56 & 0xff);
    }

}
