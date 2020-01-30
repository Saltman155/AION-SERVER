package com.aionstar.game.model.account;

import com.aionstar.game.model.items.storage.Storage;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类表示一个游戏账号包含的各类信息
 * @author saltman155
 * @date 2019/11/1 23:56
 */

public class Account {

    /**账号id*/
    private int accountId;
    /**账号*/
    private String accountName;
    /**权限等级*/
    private byte accessLevel;
    /**会员等级*/
    private byte membership;
    /**账号时间*/
    private AccountTime accountTime;
    /**账号仓库信息*/
    private Storage accountWarehouse;
    /**天族角色数量*/
    private int numberOfAsmos;
    /**魔族角色数量*/
    private int numberOfElyos;
    /**账号下属角色信息*/
    private Map<Integer,AccountPlayerData> players = new HashMap<>();

    public Map<Integer, AccountPlayerData> getPlayers() {
        return players;
    }
}
