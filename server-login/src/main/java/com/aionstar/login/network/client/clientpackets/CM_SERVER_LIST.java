package com.aionstar.login.network.client.clientpackets;

import com.aionstar.login.SpringContext;
import com.aionstar.login.controller.AccountController;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.LoginAuthResponse;
import com.aionstar.login.network.client.serverpackets.SM_LOGIN_FAIL;
import com.aionstar.login.utils.ChannelUtil;
import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 游戏客户端请求游戏服务器列表封包
 * @author saltman155
 * @date 2019/10/26 2:25
 */

public class CM_SERVER_LIST extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_SERVER_LIST.class);

    private static final int OPCODE = 0x05;

    private int accountId;

    private int loginSession;

    public CM_SERVER_LIST(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        ClientChannelAttr.SessionKey key = channel.attr(ClientChannelAttr.SESSION_KEY).get();
        //登录检查通过，则获取相关的服务器，并传输给客户端
        if(key.checkLogin(accountId,loginSession)){
            SpringContext.getContext().getBean(AccountController.class).loadGameServerCharacters(accountId);
        }else{
            logger.warn("这个ip的用户session对不上了：{}", ChannelUtil.getIp(channel));
            channel.writeAndFlush(new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR))
                    .addListener((GenericFutureListener<? extends Future<? super Void>>)(future-> channel.close()));
        }
    }

    @Override
    protected void readData() {
        accountId = data.readIntLE();
        loginSession = data.readIntLE();
        //这边还能再取一个数据，但不知道是做什么的
        data.readIntLE();
    }
}
