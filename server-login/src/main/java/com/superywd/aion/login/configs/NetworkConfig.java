package com.superywd.aion.login.configs;

import com.superywd.aion.commons.properties.Property;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/14 22:24
 */

public class NetworkConfig {

    /**账号字符集*/
    public static String accountCharset;

    /**最短登录间隔时间*/
    public static int fastReconnectionTime;

    /**登录服务器端口*/
    public static int loginServerPort;

    /**登录服务器监听地址*/
    public static String loginServerBindAddress;

    /**游戏主体服务器端口*/
    public static int gameServerPort;

    /**游戏主体服务器监听地址*/
    public static String gameServerBindAddress;

    /***/
    public static int loginTryBeforeBan;

    public static int wrongLoginBanTime;

}
