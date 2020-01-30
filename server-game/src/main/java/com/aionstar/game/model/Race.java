package com.aionstar.game.model;

/**
 * 种族&类型
 * NPC应该具有种族的属性，一个种族的npc一般会聚集在一起
 * @author saltman155
 * @date 2020/1/30 15:13
 */

public enum Race {

    /** 玩家种族 */

    //天族
    ELYOS(0),
    //魔族
    ASMOS(1),

    /** NPC种族 */

    //莱卡恩
    LYCAN(2),
    //构造体
    CONSTRUCT(3),
    //运输器 WTF？
    CARRIER(4),
    //德拉坎（高级龙类）
    DRAKAN(5),
    //德拉克纽特（低级龙类）
    LIZARDMAN(6),
    //传送点
    TELEPORTER(7),
    //娜迦（中级龙类）
    NAGA(8),
    //布洛尼
    BROWNIE(9),
    //克拉尔
    KRALL(10),
    //术古
    SHULACK(11),
    //屏障
    BARRIER(12),

    //天族要塞城门
    PC_LIGHT_CASTLE_DOOR(13),
    //魔族要塞城门
    PC_DARK_CASTLE_DOOR(14),
    //龙族要塞城门
    DRAGON_CASTLE_DOOR(15),

    //天族守护神将
    GCHIEF_LIGHT(16),
    //魔族守护神将
    GCHIEF_DARK(17),
    //龙族（要塞守卫类型）
    DRAGON(18),

    //一些中立的生物
    OUTSIDER(19),
    //姆姆
    RATMAN(20),
    //人型怪
    DEMIHUMANOID(21),
    //不死族
    UNDEAD(22),
    //野兽
    BEAST(23),
    //魔法类怪物
    MAGICALMONSTER(24),
    //元素类怪物
    ELEMENTAL(25),


    /** 一些比较特殊的 */

    NONE(26),
    PC_ALL(27);

    /**d*/
    private Integer raceId;
    /**种族说明id*/
    private Integer descriptionId;

    Race(int raceId) {
        this(raceId, null);
    }

    Race(int raceId, Integer descriptionId){
        this.raceId = raceId;
        this.descriptionId = descriptionId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getDescriptionId() {
        return descriptionId;
    }

}
