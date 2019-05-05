package com.superywd.aion.model.templates.npc;

/**
 * npc的攻击类型
 * @author: saltman155
 * @date: 2019/5/5 1:49
 */
public enum NpcType {
    /** 可攻击的（一般用于正常的怪物） */
    ATTACKABLE(0),
    /** 和平的npc */
    PEACE(2),
    /** 会主动攻击的怪物 */
    AGGRESSIVE(8),
    /** 未知*/
    INVULNERABLE(10),
    /** 不可攻击的（一般用于NPC） */
    NON_ATTACKABLE(38),
    /** 未知*/
    UNKNOWN(54);

    /**表示客户端的区分id*/
    private int someClientSideId;

    NpcType(int id) {
        this.someClientSideId = id;
    }

    public int getId() {
        return someClientSideId;
    }
}
