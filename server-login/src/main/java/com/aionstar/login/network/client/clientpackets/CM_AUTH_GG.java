package com.aionstar.login.network.client.clientpackets;

import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.LoginAuthResponse;
import com.aionstar.login.network.client.serverpackets.SM_AUTH_GG;
import com.aionstar.login.network.client.serverpackets.SM_INIT;
import com.aionstar.login.network.client.serverpackets.SM_LOGIN_FAIL;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 该数据包，是客户端对登录服务器发出的初始化数据校验包 ${@link SM_INIT } 校验通过后，发出的响应包
 * @author saltman155
 * @date 2019/10/20 17:44
 */

public class CM_AUTH_GG extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_AUTH_GG.class);

    private static final byte OPCODE = 0x07;

    private int sessionId;

    public CM_AUTH_GG(Channel channel, ByteBuf data) {
        super(OPCODE, channel,data);
    }

    @Override
    protected void handler() {
        int id = channel.id().asLongText().hashCode();
        //检查id是不是对的上
        if(id == sessionId){
            channel.attr(ClientChannelAttr.C_SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED_GG);
            channel.writeAndFlush(new SM_AUTH_GG(id));
        }else{
            // 对不上，就发送一个异常给客户端，并关闭连接
            ChannelUtil.close(channel,new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR));
        }
    }

    @Override
    protected void readData() {
        //读取客户端封包数据中的id
        sessionId = data.readIntLE();
    }
}
