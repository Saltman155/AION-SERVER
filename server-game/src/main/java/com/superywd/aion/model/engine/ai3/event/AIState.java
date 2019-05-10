package com.superywd.aion.model.engine.ai3.event;

/**
 * 该类用于声明所有的AI状态(暂时还不知道和上面的有什么区别)
 * @author: saltman155
 * @date: 2019/5/2 12:47
 */
public enum AIState {

    /**刚创造*/
    CREATED,
    /**死亡*/
    DIED,
    /**不再刷新*/
    DESPAWNED,
    /**空闲状态*/
    IDLE,
    /**行走中*/
    WALKING,
    /**跟随中*/
    FOLLOWING,
    /**撤退中*/
    RETURNING,
    /**战斗中*/
    FIGHT,
    /**逃走中*/
    FEAR,
}
