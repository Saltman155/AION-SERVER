package com.aionstar.login.network.client.clientpackets;

import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.LoginAuthResponse;
import com.aionstar.login.network.client.serverpackets.SM_AUTH_GG;
import com.aionstar.login.network.client.serverpackets.SM_LOGIN_FAIL;
import com.aionstar.login.network.client.serverpackets.SM_PLAY_FAIL;
import com.aionstar.login.network.client.serverpackets.SM_PLAY_OK;
import com.aionstar.login.service.MainServerService;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该数据包在客户端接受到登录服务器发出的 ${@link CM_SERVER_LIST} 后，发出的下一个包
 * @author saltman155
 * @date 2020/1/24 0:08
 */

public class CM_PLAY extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_PLAY.class);

    private static final byte OPCODE = 0x02;

    /**账号*/
    private int accountId;
    /**之前登录校验值*/
    private int loginSession;
    /**尝试登录的服务器id*/
    private byte serverId;

    public CM_PLAY(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        ClientChannelAttr.SessionKey key = channel.attr(ClientChannelAttr.SESSION_KEY).get();
        //校验成功
        if(key.checkLogin(accountId,loginSession)){
            MainServerInfo msi = MainServerService.getGameServer(serverId);
            //查不到或者服务器未在线
            if (msi == null || !msi.isOnline())
                channel.writeAndFlush(new SM_PLAY_FAIL(LoginAuthResponse.SERVER_DOWN));
            //已经满了
            else if( msi.isFull()) channel.writeAndFlush(new SM_PLAY_FAIL(LoginAuthResponse.SERVER_FULL));
            //成功加入服务器
            else
                channel.writeAndFlush(new SM_PLAY_OK(serverId,key));
        } else{
            //正常来讲，不会校验失败，万一失败了，返回个错误码，关闭连接
            ChannelUtil.close(channel,new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR));
        }
    }

    @Override
    protected void readData() {
        //读就完事了
        accountId = data.readIntLE();
        loginSession = data.readIntLE();
        serverId = data.readByte();
    }
}
