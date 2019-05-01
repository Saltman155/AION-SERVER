package com.superywd.aion.login.model;

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
    private String passwordHash;
    /**账号等级 0为普通用户 1为GM*/
    private Byte accessLevel;
    /**会员等级 0为非会员*/
    private Byte membership;
    /**账号活跃度*/
    private Byte activated;
    /**最后登录服务器id -1表示没有*/
    private Byte lastServer;
    /**最后登录ip*/
    private String lastIp;
    /**最后登录Mac地址*/
    private String lastMac = "xx-xx-xx-xx-xx-xx";
    /**ip登录白名单*/
    private String ipForce;
    /**游戏时间*/
    private AccountTime accountTime;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Byte getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Byte accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Byte getMembership() {
        return membership;
    }

    public void setMembership(Byte membership) {
        this.membership = membership;
    }

    public Byte getActivated() {
        return activated;
    }

    public void setActivated(Byte activated) {
        this.activated = activated;
    }

    public Byte getLastServer() {
        return lastServer;
    }

    public void setLastServer(Byte lastServer) {
        this.lastServer = lastServer;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastMac() {
        return lastMac;
    }

    public void setLastMac(String lastMac) {
        this.lastMac = lastMac;
    }

    public String getIpForce() {
        return ipForce;
    }

    public void setIpForce(String ipForce) {
        this.ipForce = ipForce;
    }

    public AccountTime getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(AccountTime accountTime) {
        this.accountTime = accountTime;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", accessLevel=" + accessLevel +
                ", membership=" + membership +
                ", activated=" + activated +
                ", lastServer=" + lastServer +
                ", lastIp='" + lastIp + '\'' +
                ", lastMac='" + lastMac + '\'' +
                ", ipForce='" + ipForce + '\'' +
                ", accountTime=" + accountTime +
                '}';
    }
}
