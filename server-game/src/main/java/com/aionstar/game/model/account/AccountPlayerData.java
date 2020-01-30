package com.aionstar.game.model.account;

import java.time.LocalDateTime;

/**
 * 该类表示一个角色包含的一些信息
 * 这些信息主要用于支持 “选择角色” 界面的角色展示
 */
public class AccountPlayerData {

    /**基础信息*/
    private PlayerCommonData playerCommonData;
    /**外貌信息*/
    private PlayerAppearance appearance;
    /**装备信息*/
    private List<Item> equipment;
    /**创建时间*/
    private LocalDateTime creationTime;
    /**删除截止时间*/
    private LocalDateTime deletionTime;
    /**军团信息*/
    private LegionMember legionMember;

    public AccountPlayerData(PlayerCommonData playerCommonData, PlayerAppearance appearance, List<Item> equipment,  LegionMember legionMember) {
        this.playerCommonData = playerCommonData;
        this.appearance = appearance;
        this.equipment = equipment;
        this.legionMember = legionMember;
    }
}
