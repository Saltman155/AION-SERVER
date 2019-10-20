package com.superywd.aion.login.network.aion.clientpackets;

import com.superywd.aion.login.network.aion.ClientPacket;
import com.superywd.aion.login.network.aion.LoginAuthResponse;
import com.superywd.aion.login.network.aion.SessionState;
import com.superywd.aion.login.network.aion.serverpackets.SM_AUTH_GG;
import com.superywd.aion.login.network.aion.serverpackets.SM_LOGIN_FAIL;
import com.superywd.aion.login.network.handler.client.ClientChannelInitializer;
import io.netty.channel.Channel;
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
            logger.info("id吻合，验证通过...");
            channel.attr(ClientChannelInitializer.SESSION_STATE).set(SessionState.AUTHED_GG);
            channel.writeAndFlush(new SM_AUTH_GG(id));
        }else{
            // 对不上，就发送一个异常给客户端
            //TODO 这里感觉是不是会有问题，我想实现的实际上是一个写出并关闭的过程, 万一它还没写出去就关了...
            channel.writeAndFlush(new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR));
            channel.close();
        }
    }

    @Override
    protected void readData() {
        //读取客户端封包数据中的id
        sessionId = data.getInt();
    }
}
