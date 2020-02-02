package com.aionstar.game.model.gameobjects.player;

import com.aionstar.game.model.Gender;
import com.aionstar.game.model.PlayerClass;
import com.aionstar.game.model.Race;

/**
 * 这个类包含了角色的一些基本信息，即使该角色没有在线也可以使用
 */
public class PlayerCommonData {

    private int playerId;                   //角色ID
    private Race race;                      //角色种族
    private String name;                    //角色名称
    private Gender gender;                  //角色性别

    private PlayerClass playerClass;        //角色职业

    private int level = 0;                  //角色等级
    private long exp = 0;                   //当前经验
    private long expRecoverable = 0;        //可修补的经验

    private long lastOnline;                //最后在线时间
    private boolean online;                 //当前是否在线
    private String note;                    //每日一句

}
