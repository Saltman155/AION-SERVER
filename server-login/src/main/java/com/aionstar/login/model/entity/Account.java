package com.aionstar.login.model.entity;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:10
 */

public class Account {

    /**用户id*/
    private Integer id;
    /**用户昵称*/
    private String name;
    /**用户密码（MD5加密）*/
    private String password;
    /**上一次登录服务器ip*/
    private byte lastServer;
    /**用户权限等级*/
    private byte accessLevel;
    /**用户会员等级*/
    private byte membership;
    /**虚拟货币*/
    private long toll;
    /**游戏时间*/
    private AccountTime accountTime;

    public Account() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public byte getLastServer() { return lastServer; }

    public void setLastServer(byte lastServer) { this.lastServer = lastServer; }

    public byte getAccessLevel() { return accessLevel; }

    public void setAccessLevel(byte accessLevel) { this.accessLevel = accessLevel; }

    public byte getMembership() { return membership; }

    public void setMembership(byte membership) { this.membership = membership; }

    public long getToll() { return toll; }

    public void setToll(long toll) { this.toll = toll; }

    public AccountTime getAccountTime() { return accountTime; }

    public void setAccountTime(AccountTime accountTime) { this.accountTime = accountTime; }

}
