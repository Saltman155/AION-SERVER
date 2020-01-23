package com.aionstar.game.model.entity;

/**
 * @author saltman155
 * @date 2019/10/24 2:59
 */

public class Player {

    private Integer id;

    private Integer accountId;

    private String name;

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
