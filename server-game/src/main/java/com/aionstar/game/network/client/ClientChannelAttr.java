package com.aionstar.game.network.client;

import com.aionstar.commons.network.BaseChannelAttr;
import io.netty.util.AttributeKey;

/**
 * 客户端连接的一些会话属性信息
 * @author saltman155
 * @date 2019/11/1 23:46
 */

public class ClientChannelAttr extends BaseChannelAttr {

    /**当前登录的用户*/
    public static final AttributeKey<Integer> ACCOUNT_ID = AttributeKey.newInstance("accountId");
    /**当前会话的状态*/
    public static final AttributeKey<SessionState> SESSION_STATE = AttributeKey.newInstance("sessionState");


    public static enum SessionState{
        /**仅连接*/
        CONNECTED,
        /**已验证*/
        AUTHED,
        /**已进入游戏*/
        ONLINE
    }

}
