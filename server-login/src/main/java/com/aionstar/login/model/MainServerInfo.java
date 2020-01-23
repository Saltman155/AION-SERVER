package com.aionstar.login.model;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.model.IPRange;
import com.aionstar.login.model.entity.Account;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏服务器对象
 * @author saltman155
 * @date 2019/11/1 23:31
 */

public class MainServerInfo {

    /**游戏服务器id*/
    private byte id;
    /**游戏服务器名称*/
    private String name;
    /**游戏服务器地址*/
    private String ip;
    /**游戏服务器对客户端暴露的端口*/
    private Integer clientPort;
    /**游戏服务器密码*/
    private String password;
    /**默认的什么东西*/
    private byte[] defaultAddress;
    /**游戏服务器用户登录IP范围*/
    private List<IPRange> ipRanges;
    /**服务器人数上限*/
    private int maxPlayers;
    /**当前在线用户*/
    private Map<Integer, Account> onlineUser = new HashMap<>();
    /**与登录服务器的会话连接*/
    private Channel loginConnection;


    /**
     * 当前游戏服务器是否在线
     * @return      是否在线
     */
    public final boolean isOnline() {
        return loginConnection.isActive() &&
                MSChannelAttr.InnerSessionState.AUTHED
                        .equals(loginConnection.attr(MSChannelAttr.M_SESSION_STATE).get());
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
        //蛇皮操作 游戏服务器不在线，就返回本地ip
        if (!isOnline()) {
            return new byte[] { 127, 0, 0, 1 };
        }
        //否则就从 ipRange 里查询，然后返回
        for (IPRange ipr : ipRanges) {
            if (ipr.isInRange(playerIp)) {
                return ipr.getAddress();
            }
        }
        //如果找不到，就返回默认的
        return defaultAddress;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public byte[] getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(byte[] defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<IPRange> getIpRanges() {
        return ipRanges;
    }

    public void setIpRanges(List<IPRange> ipRanges) {
        this.ipRanges = ipRanges;
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
