package com.aionstar.login.network.handler.client;

import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.crypt.LBlowfishCipher;
import com.aionstar.login.network.crypt.XORCheckUtil;
import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 客户端消息解码器
 * @author saltman155
 * @date 2019/10/20 16:45
 */

public class ClientMessageDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageDecoder.class);

    private final BasePacketFactory packetHandler;

    public ClientMessageDecoder(BasePacketFactory handler){
        this.packetHandler = handler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Channel channel = ctx.channel();
        int dataLen = in.readableBytes();
        ByteBuf data = channel.attr(ClientChannelAttr.BUFFER).get();
        in.readBytes(data,0,dataLen);
        data.readerIndex(0).writerIndex(dataLen);
        //获取之前存的cipher与state
        LBlowfishCipher cipher = channel.attr(ClientChannelAttr.BLOWFISH_CIPHER).get();
        //校验不通过直接断开连接
        if(cipher == null || !decrypt(cipher,data)){
            logger.error("id为 {} 的channel发送的数据包校验失败！",channel.id().asLongText().hashCode());
            ctx.channel().close();
            return;
        }
        //然后生成具体的Client包对象
        ClientPacket packet = packetHandler.handle(data,channel);
        if(packet != null){
            out.add(packet);
        }else {
            logger.warn("收到无效数据包！");
        }
    }

    /**
     * 数据包解密
     * @param cipher        解密器
     * @param data          带解密的数据
     * @return              是否解密成功
     */
    private boolean decrypt(LBlowfishCipher cipher,ByteBuf data){
        //解密客户端数据包
        cipher.decipher(data.array(),0,data.writerIndex());
        //校验数据包
        return XORCheckUtil.verifyChecksum(data.array(),0,data.writerIndex());
    }

}
