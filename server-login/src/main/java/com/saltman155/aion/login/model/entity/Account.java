package com.saltman155.aion.login.model.entity;

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

    private byte lastServer;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getLastServer() {
        return lastServer;
    }

    public void setLastServer(byte lastServer) {
        this.lastServer = lastServer;
    }
}
