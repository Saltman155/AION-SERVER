package com.saltman155.aion.login.network.gameserver;

import io.netty.util.AttributeKey;

/**
 * 表示一些channel的属性
 * @author saltman155
 * @date 2019/11/16 20:55
 */

public class GSChannelAttr {

    /**当前会话的状态*/
    public static final AttributeKey<SessionState> SESSION_STATE = AttributeKey.newInstance("sessionState");

    /**服务端存储的会话状态*/
    public enum SessionState {
        //表示与游戏服务端仅仅是建立连接
        CONNECTED,
        //表示游戏服务端已经通过连接验证
        AUTHED
    }

}
