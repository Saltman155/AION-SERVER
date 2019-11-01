package com.saltman155.aion.login.model;

import com.saltman155.aion.login.model.entity.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏服务器对象
 * @author saltman155
 * @date 2019/11/1 23:31
 */

public class GameServerInfo {

    /**游戏服务器id*/
    private byte id;
    /**游戏服务器地址*/
    private String address;
    /**游戏服务器对客户端暴露的端口*/
    private String clientPort;

    private String allowIp;

    private String password;
    /**服务器人数上限*/
    private int maxPlayers;
    /**当前在线用户*/
    private Map<Integer, Account> onlineUsers;

    {
        onlineUsers = new ConcurrentHashMap<>();
    }



}
