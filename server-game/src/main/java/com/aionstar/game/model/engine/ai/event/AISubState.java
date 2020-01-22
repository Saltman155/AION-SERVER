package com.aionstar.game.model.engine.ai.event;

/**
 * 该类用于声明所有的AI的子状态
 * @author: saltman155
 * @date: 2019/5/2 12:52
 */
public enum AISubState {

    /**无*/
    NONE,
    /**谈论*/
    TALK,

    CAST,

    WALK_PATH,

    WALK_RANDOM,

    WALK_WAIT_GROUP,

    FREEZE
}
