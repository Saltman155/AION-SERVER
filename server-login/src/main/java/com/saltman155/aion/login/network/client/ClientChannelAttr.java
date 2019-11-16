package com.saltman155.aion.login.network.client;

import com.saltman155.aion.login.model.entity.Account;
import com.saltman155.aion.login.network.crypt.EncryptedRSAKeyPair;
import com.saltman155.aion.login.network.crypt.LBlowfishCipher;
import io.netty.util.AttributeKey;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 该类用于存储与客户端连接的channel的一些session属性
 * @author saltman155
 * @date 2019/10/26 1:23
 */

public class ClientChannelAttr {

    /**当前会话的状态*/
    public static final AttributeKey<SessionState> SESSION_STATE = AttributeKey.newInstance("sessionState");
    /**当前会话key*/
    public static final AttributeKey<SessionKey> SESSION_KEY = AttributeKey.newInstance("sessionKey");
    /**用于加解密的blowfish*/
    public static final AttributeKey<LBlowfishCipher> BLOWFISH_CIPHER = AttributeKey.newInstance("blowfishCipher");
    /**用于加解密的rsa*/
    public static final AttributeKey<EncryptedRSAKeyPair> RSA_KEY = AttributeKey.newInstance("rsaKey");
    /**当前会话的用户*/
    public static final AttributeKey<Account> ACCOUNT = AttributeKey.newInstance("account");

    /**服务端存储的会话状态*/
    public static enum SessionState {
        //表示与客户端仅仅是建立连接
        CONNECTED,
        //表示已经通过了客户端的GameGuard验证
        AUTHED_GG,
        //表示已经登录
        AUTHED_LOGIN
    }

    /**客户端维护登录状态需要的session信息*/
    public static class SessionKey{

        public final int accountId;

        public final int loginSession;

        public final int playSession1;

        public final int playSession2;

        public SessionKey(int accountId) {
            this.accountId = accountId;
            loginSession = ThreadLocalRandom.current().nextInt();
            playSession1 = ThreadLocalRandom.current().nextInt();
            playSession2 = ThreadLocalRandom.current().nextInt();
        }

        public SessionKey(int accountId, int loginSession, int playSession1, int playSession2) {
            this.accountId = accountId;
            this.loginSession = loginSession;
            this.playSession1 = playSession1;
            this.playSession2 = playSession2;
        }

        /**
         * 检查登录凭证是否有效
         * @param accountId     账号
         * @param loginSession  登录凭证
         * @return              是否有效
         */
        public boolean checkLogin(int accountId, int loginSession) {
            return this.accountId == accountId && this.loginSession == loginSession;
        }

        /**
         * 检查完整的登录凭证
         * @param key           待检查的登录凭证
         * @return              是否符合
         */
        public boolean checkSessionKey(SessionKey key) {
            return (loginSession == key.loginSession && accountId == key.accountId && playSession1 == key.playSession1 && playSession2 == key.playSession2);
        }

    }

}
