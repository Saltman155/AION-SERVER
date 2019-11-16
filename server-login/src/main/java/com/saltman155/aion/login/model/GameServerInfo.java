package com.saltman155.aion.login.model;

import com.saltman155.aion.login.model.entity.Account;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏服务器对象
 * @author saltman155
 * @date 2019/11/1 23:31
 */

public class GameServerInfo {

    /**游戏服务器id*/
    private byte id;
    /**游戏服务器地址*/
    private String ip;
    /**游戏服务器对客户端暴露的端口*/
    private Integer clientPort;
    /**游戏服务器密码*/
    private String password;
    /**游戏服务器用户登录IP范围*/
    private String allowIp;
    /**服务器人数上限*/
    private int maxPlayers;
    /**当前在线用户*/
    private Map<Integer, Account> onlineUsers = new HashMap<>();
    /**与登录服务器的会话连接*/
    private Channel loginConnection;
    /**该服务器当前在线用户*/
    private final Map<Integer, Account> onlineUser = new HashMap<Integer, Account>();


    /**
     * 当前游戏服务器是否在线
     * @return
     */
    public final boolean isOnline() {
        return false;
    }

    public int getCurrentPlayers() {
        return onlineUser.size();
    }

    /**
     * 根据玩家的ip来返回其可以访问的该服务器的ip
     * 玩家往往从各个不同的子网来访问服务器，因此我们需要发送相对应的服务器ip地址给他们
     * 如果游戏服务器不在线，则直接返回127.0.0.1
     * @param playerIp 玩家的ip
     * @return  对应的游戏服务器ip
     */
    public byte[] getIpAddressForPlayerIp(String playerIp){
        return null;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Channel getLoginConnection() {
        return loginConnection;
    }

    public void setLoginConnection(Channel connection) {
        this.loginConnection = connection;
    }
}
