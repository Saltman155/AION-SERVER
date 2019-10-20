package com.superywd.aion.login.network.handler.client;

import com.superywd.aion.login.network.aion.SessionState;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.superywd.aion.login.network.crypt.LBlowfishCipher;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 连接handler初始化
 * @author saltman155
 * @date 2019/10/10 1:05
 */

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    public static final AttributeKey<SessionState> SESSION_STATE = AttributeKey.newInstance("sessionState");

    public static final AttributeKey<LBlowfishCipher> BLOWFISH_CIPHER = AttributeKey.newInstance("blowfishCipher");

    public static final AttributeKey<EncryptedRSAKeyPair> RSA_KEY = AttributeKey.newInstance("rsaKey");

    @Resource
    private ClientChannelHandler clientChannelHandler;
    @Resource
    private ClientMessageEncoder clientMessageEncoder;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline().addLast();
        //服务端出站消息编码器
        pipeline.addLast(clientMessageEncoder);
        //客户端入站Frame编码器
        pipeline.addLast(new ClientPacketFrameDecoder());
        //客户端入站消息解码器
        pipeline.addLast(new ClientMessageDecoder());
        //客户端数据包处理
        pipeline.addLast(clientChannelHandler);
    }
}
