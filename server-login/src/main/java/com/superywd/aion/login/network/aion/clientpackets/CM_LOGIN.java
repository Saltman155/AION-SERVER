package com.superywd.aion.login.network.aion.clientpackets;

import com.superywd.aion.login.controller.AccountController;
import com.superywd.aion.login.network.aion.ClientPacket;
import com.superywd.aion.login.network.aion.LoginAuthResponse;
import com.superywd.aion.login.network.aion.serverpackets.SM_LOGIN_FAIL;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.superywd.aion.login.network.handler.client.ClientChannelInitializer;
import com.superywd.aion.login.utils.SpringManager;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;

/**
 * 这个是和客户端一通操作后，客户端发来的用户登录信息数据包，里面包含账号密码等信息
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
            EncryptedRSAKeyPair keyPair = channel.attr(ClientChannelInitializer.RSA_KEY).get();
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
        logger.info("用户尝试登录！账号: {} | 密码: {}",user,password);
        LoginAuthResponse response = SpringManager.getContext().getBean(AccountController.class).userLogin(user,password,channel);
        switch (response){
            case AUTHED:break;
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
