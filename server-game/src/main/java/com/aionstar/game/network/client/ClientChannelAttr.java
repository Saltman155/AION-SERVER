package com.aionstar.game.network.client;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.game.model.account.Account;
import com.aionstar.game.model.entity.Player;
import com.aionstar.game.network.crypt.ClientCrypt;
import io.netty.util.AttributeKey;

/**
 * 客户端连接的一些会话属性信息
 * @author saltman155
 * @date 2019/11/1 23:46
 */

public class ClientChannelAttr extends BaseChannelAttr {

    /**当前登录的用户*/
    public static final AttributeKey<Account> ACCOUNT = AttributeKey.newInstance("account");
    /**当前会话的状态*/
    public static final AttributeKey<SessionState> SESSION_STATE = AttributeKey.newInstance("sessionState");
    /**用于加解密的连接key*/
    public static final AttributeKey<ClientCrypt> CRYPT = AttributeKey.newInstance("crypt");
    /**连接上在线的用户*/
    public static final AttributeKey<Player> ACTIVE_PLAYER = AttributeKey.newInstance("activePlayer");
    /**硬件地址*/
    public static final AttributeKey<String> MAC_ADDRESS = AttributeKey.newInstance("macAddress");

    public static enum SessionState{
        /**仅连接*/
        CONNECTED,
        /**已验证*/
        AUTHED,
        /**已进入游戏*/
        ONLINE
    }

}
