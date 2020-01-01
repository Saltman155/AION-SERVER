package com.saltman155.aion.login.network.handler.client;

import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.factories.ClientPacketHandlerFactory;
import com.saltman155.aion.login.network.client.ClientPacket;
import com.saltman155.aion.login.network.crypt.LBlowfishCipher;
import com.saltman155.aion.login.network.crypt.XORCheckUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * 客户端消息解码器
 * @author saltman155
 * @date 2019/10/20 16:45
 */

public class ClientMessageDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("收到客户端消息！");
        int dataLen = in.readableBytes();
        ByteBuffer data = ByteBuffer.allocate(ClientPacket.MAX_PACKET_SIZE);
        //这里是个坑，记得设置成小端模式
        data.order(ByteOrder.LITTLE_ENDIAN);
        in.readBytes(data.array(),0,dataLen);
        data.position(0).limit(dataLen);
        Channel channel = ctx.channel();
        //获取之前存的cipher与state
        LBlowfishCipher cipher = channel.attr(ClientChannelAttr.BLOWFISH_CIPHER).get();
        //校验不通过直接断开连接
        if(cipher == null || !decrypt(cipher,data)){
            logger.error("id为 {} 的channel发送的数据包校验失败！",channel.id().asLongText().hashCode());
            ctx.channel().close();
        }
        //然后生成具体的Client包对象
        ClientPacket packet = ClientPacketHandlerFactory.handle(data,channel);
        out.add(packet);
    }

    /**
     * 数据包解密
     * @param cipher        解密器
     * @param data          带解密的数据
     * @return              是否解密成功
     */
    private boolean decrypt(LBlowfishCipher cipher,ByteBuffer data){
        //解密客户端数据包
        cipher.decipher(data.array(),data.position(),data.limit());
        //校验数据包
        return XORCheckUtil.verifyChecksum(data.array(),data.position(),data.limit());
    }

}
