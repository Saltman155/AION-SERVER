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
        buf.array();
        return false;
    }

    /**
     * 数据包加密
     * @param buf   待加密的数据
     */
    public final void encrypt(ByteBuf buf){

    }

}
