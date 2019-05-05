package com.superywd.aion.model.gameobjects;

/**
 * 这个类是游戏中所有的“对象”的基类。“对象”指的是玩家可以与之
 * 交互的，如NPC,怪物，其他玩家，物品等等。
 * @author: saltman155
 * @date: 2019/5/1 21:28
 */
public abstract class AionObject {

    /**唯一id*/
    private Integer objectId;

    public AionObject(Integer objectId) {
        this.objectId = objectId;
    }

    /**
     * 获取该对象的唯一id
     * @return  唯一id
     */
    public Integer getObjectId() {
        return objectId;
    }

    /**
     * 返回对象的名称
     * 该名称对于玩家而言应该是玩家的用户名
     * 对于物品、怪物来说应该是预设的值
     * @return  对象名称
     */
    public abstract String getName();
}
