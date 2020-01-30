package com.aionstar.game.model.gameobjects.player;

import com.aionstar.game.model.PlayerClass;
import com.aionstar.game.model.Race;

/**
 * 这个类包含了角色的一些基本信息，即使该角色没有在线也可以使用
 */
public class PlayerCommonData {

    private int playerId;                   //角色ID
    private Race race;                      //角色种族
    private String name;                    //角色名称

    private PlayerClass playerClass;

    private int level = 0;                  //角色等级
    private long exp = 0;                   //当前经验

}
