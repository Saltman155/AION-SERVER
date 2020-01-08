package com.saltman155.aion.login.network.client.clientpackets;

import com.saltman155.aion.login.SpringContext;
import com.saltman155.aion.login.controller.AccountController;
import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.commons.network.packet.ClientPacket;
import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.network.client.serverpackets.SM_LOGIN_FAIL;
import com.saltman155.aion.login.network.client.serverpackets.SM_LOGIN_OK;
import com.saltman155.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;

/**
 * 客户端请求登陆封包
 * 这是和客户端一通操作后，客户端发来的用户登录信息数据包，里面包含账号密码等信息
 * @author saltman155
 * @date 2019/10/20 20:01
 */

public class CM_LOGIN extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_LOGIN.class);

    private static final int OPCODE = 0x0b;

    private static final int ACCOUNT_DATA_SIZE = 128;

    private byte[] accountData;

    public CM_LOGIN(Channel channel, ByteBuffer data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        if(accountData == null){
            return;
        }
        try{
            //获取本次连接起初协定的rsa密钥对
            EncryptedRSAKeyPair keyPair = channel.attr(ClientChannelAttr.RSA_KEY).get();
            Cipher cipher = Cipher.getInstance("RSA/ECB/nopadding");
            cipher.init(Cipher.DECRYPT_MODE,keyPair.getRsaKeyPair().getPrivate());
            //一通解密
            accountData = cipher.doFinal(accountData,0,ACCOUNT_DATA_SIZE);
        }catch (Exception e){
            //有啥异常，发就完事了
            channel.writeAndFlush(new SM_LOGIN_FAIL(LoginAuthResponse.SYSTEM_ERROR));
            return;
        }
        String user = new String(accountData, 64, 32).trim().toLowerCase();
        String password = new String(accountData, 96, 32).trim();
        logger.info("用户尝试登录！账号: {} | 密码: {} | ip: {}",
                user,password, ChannelUtil.getIp(channel));
        LoginAuthResponse response = SpringContext.getContext()
                .getBean(AccountController.class).userLogin(user,password,channel);
        switch (response){
            case AUTHED:
                //取出登录成功后业务层放置的sessionKey
                ClientChannelAttr.SessionKey key = channel.attr(ClientChannelAttr.SESSION_KEY).get();
                channel.writeAndFlush(new SM_LOGIN_OK(key));
                logger.info("用户 {} 登录成功！",user);
                break;
            case INVALID_PASSWORD:
                //这边原版还做了一个反复登录失败的次数限制，我这里就先不做了
                channel.writeAndFlush(new SM_LOGIN_FAIL(LoginAuthResponse.INVALID_PASSWORD));
                break;
            default:
                //发送数据包，并关闭连接
                channel.writeAndFlush(new SM_LOGIN_FAIL(response))
                        .addListener((GenericFutureListener<? extends Future<? super Void>>) channel.close());
        }

    }

    @Override
    protected void readData() {
        data.getInt();
        //读取出账号和密码等信息,存在accountData里
        if(data.remaining() >= ACCOUNT_DATA_SIZE){
            accountData = new byte[ACCOUNT_DATA_SIZE];
            data.get(accountData,0,ACCOUNT_DATA_SIZE);
        }
    }
}
