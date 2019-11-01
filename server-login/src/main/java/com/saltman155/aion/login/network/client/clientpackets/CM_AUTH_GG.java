package com.saltman155.aion.login.network.client.clientpackets;

import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.ClientPacket;
import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.network.client.serverpackets.SM_AUTH_GG;
import com.saltman155.aion.login.network.client.serverpackets.SM_LOGIN_FAIL;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 该数据包，是客户端成功响应登录服务器发出的数据校验包后，发出的响应包
 * @author saltman155
 * @date 2019/10/20 17:44
 */

public class CM_AUTH_GG extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_AUTH_GG.class);

    private static final int OPCODE = 0x07;

    private int sessionId;

    public CM_AUTH_GG(Channel channel, ByteBuffer data) {
        super(OPCODE, channel,data);
    }

    @Override
    protected void handler() {
        int id = channel.id().asLongText().hashCode();
        //检查id是不是对的上
        if(id == sessionId){
            channel.attr(ClientChannelAttr.SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED_GG);
            channel.writeAndFlush(new SM_AUTH_GG(id));
        }else{
            // 对不上，就发送一个异常给客户端，并关闭连接
            channel.writeAndFlush(new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR))
            .addListener((GenericFutureListener<? extends Future<? super Void>>) channel.close());
        }
    }

    @Override
    protected void readData() {
        //读取客户端封包数据中的id
        sessionId = data.getInt();
    }
}
