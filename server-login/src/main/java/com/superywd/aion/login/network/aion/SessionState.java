package com.superywd.aion.login.network.aion;

/**
 * 表示当前与客户端的会话的状态
 * @author saltman155
 * @date 2019/10/20 17:10
 */

public enum SessionState {
    //表示与客户端仅仅是建立连接
    CONNECTED,
    //表示已经通过了客户端的GameGuard验证
    AUTHED_GG,
    //表示已经登录
    AUTHED_LOGIN
}
