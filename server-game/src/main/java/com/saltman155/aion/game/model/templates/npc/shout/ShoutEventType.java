package com.saltman155.aion.game.model.templates.npc.shout;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * 这个类用于描述NPC在呐喊时的几个场景
 * @author: saltman155
 * @date: 2019/5/6 22:00
 */

@XmlType(name = "ShoutEventType")
@XmlEnum
public enum ShoutEventType {

    /**空闲时（空闲时一般不会呐喊，除了那种公告官npc）*/
    IDLE,
    /**处于攻击状态*/
    ATTACKED,
    /**攻击开始时*/
    ATTACK_BEGIN,
    /**攻击结束时*/
    ATTACK_END,
    /**暴击时*/
    ATTACK_K,
    /**召唤攻击时*/
    SUMMON_ATTACK,

    CASTING,
    CAST_K,						// Numeric cast shouts
    DIED,							// Npc died
    HELP,							// Calls help without running away
    HELPCALL,					// Calls help and runs away
    WALK_WAYPOINT,		// Reached the walk point
    START,
    WAKEUP,
    SLEEP,
    RESET_HATE,
    UNK_ACC,					// Not clear but seems the same as ATTACKED
    WALK_DIRECTION,		// NPC reached the 0 walk point
    STATUP,						// Skill statup shouts
    SWITCH_TARGET,		// NPC switched the target
    SEE,							// NPC sees a player from aggro range
    PLAYER_MAGIC,			// Player uses magic attack (merge with attacked?)
    PLAYER_SNARE,
    PLAYER_DEBUFF,
    PLAYER_SKILL,
    PLAYER_SLAVE,
    PLAYER_BLOW,
    PLAYER_PULL,
    PLAYER_PROVOKE,
    PLAYER_CAST,
    GOD_HELP,
    LEAVE,						// when player leaves an attack
    BEFORE_DESPAWN,		// NPC despawns
    ATTACK_DEADLY,
    WIN,
    ENEMY_DIED;				// NPC's enemy died


}
