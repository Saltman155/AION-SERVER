package com.superywd.aion.login.network.handler.client;

import com.superywd.aion.login.network.aion.ServerPacket;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.superywd.aion.login.network.crypt.LKeyGenerator;
import com.superywd.aion.login.network.crypt.XORCheckUtil;
import com.superywd.aion.login.network.crypt.LBlowfishCipher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 客户端通信编码器
 * 针对服务端发送给客户端的数据包 {@link com.superywd.aion.login.network.aion.ServerPacket} 对其进行二进制序列化
 *
 *
 *
 * @author saltman155
 * @date 2019/10/10 1:13
 */


@Component
@ChannelHandler.Sharable
public class ClientMessageEncoder extends MessageToByteEncoder<ServerPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageEncoder.class);

    public static final AttributeKey<LBlowfishCipher> BLOWFISH_CIPHER = AttributeKey.newInstance("blowfishCipher");
    public static final AttributeKey<EncryptedRSAKeyPair> RSA_KEY = AttributeKey.newInstance("rsaKey");

    @Resource
    private LKeyGenerator keyGenerator;

    /**
     * 加密数据包
     * @param ctx           channel上下文
     * @param packet        待发送数据包
     * @param out           channel输出流
     * @throws Exception    随意抛出
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket packet, ByteBuf out) throws Exception {
        Channel channel = ctx.channel();
        //获取数据包数据
        ByteBuffer buffer = packet.getData();
        byte[] data = buffer.array();
        //计算最终封包长度与数据长度
        int packetLen = calLastPacketLen(buffer.position());
        int dataLen = packetLen - 2;
        //如果连接没有携带key，则是初次创建连接，设置两个key
        if(!channel.hasAttr(BLOWFISH_CIPHER)){
            LBlowfishCipher cipher = new LBlowfishCipher(LKeyGenerator.BLOWFISH_INIT_KEY);
            //添加异或校验
            XORCheckUtil.encXORPass(data,2,dataLen, ThreadLocalRandom.current().nextInt());
            //加密数据域
            cipher.cipher(data,2,dataLen);
            //加密完成后，用新密钥更新cipher以便下一次加密&解密时使用
            cipher.updateKey(packet.getBlowfishKey().getEncoded());
            channel.attr(BLOWFISH_CIPHER).set(cipher);
            channel.attr(RSA_KEY).set(packet.getRsaPublicKey());
        } else{
            //获取到之前设置的cipher来加密
            LBlowfishCipher cipher = channel.attr(BLOWFISH_CIPHER).get();
            XORCheckUtil.appendChecksum(data,2,dataLen);
            cipher.cipher(data,2,dataLen);
        }
        //在头部写入封包长度
        buffer.putShort(0,(short)(packetLen));
        buffer.position(0).limit(packetLen);
        //数据写出
        out.writeBytes(buffer);
    }


    /**
     * 计算封包最终长度
     * 最终的数据包需要满足两个要求：
     *     [1]封包的大小是8字节的整数倍 + 2 （头两个字节存储封包长度）
     *     [2]需要在尾部留出至少4个字节空间来存储异或校验码
     * @param before    未处理前的封包长度
     * return 添加校验数据后的封包大小
     */
    private int calLastPacketLen(int before){
        final int checkDataLen = 4;
        int size = (before + checkDataLen - 2);
        int padding = (8 - (size) % 8);
        size = size + (padding == 8 ? 0 : padding);
        return size + 2;
    }

}
