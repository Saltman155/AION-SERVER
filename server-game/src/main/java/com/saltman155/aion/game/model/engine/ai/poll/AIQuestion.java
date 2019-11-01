package com.saltman155.aion.game.model.engine.ai.poll;

/**
 * 该类描述对AI的所有可行性调查种类
 * @author: saltman155
 * @date: 2019/5/5 20:22
 */
public enum AIQuestion {

    /**是否到达目的地*/
    DESTINATION_REACHED,
    /**是否在白天刷新*/
    CAN_SPAWN_ON_DAYTIME_CHANGE,
    /**是否应该消亡*/
    SHOULD_DECAY,
    /**是否可以重新刷新*/
    SHOULD_RESPAWN,
    /**是否有奖赏（掉落？）*/
    SHOULD_REWARD,
    /**是否奖赏深渊点*/
    SHOULD_REWARD_AP,
    /**是否能够抵抗异常*/
    CAN_RESIST_ABNORMAL,
    /**是否会攻击玩家*/
    CAN_ATTACK_PLAYER,
    /**是否会说话*/
    CAN_SHOUT
}
