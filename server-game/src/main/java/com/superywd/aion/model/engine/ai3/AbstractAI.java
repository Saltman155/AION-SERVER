package com.superywd.aion.model.engine.ai3;

import com.superywd.aion.model.engine.ai3.enums.AIEventType;
import com.superywd.aion.model.engine.ai3.enums.AIState;
import com.superywd.aion.model.engine.ai3.enums.AISubState;
import com.superywd.aion.model.gameobjects.Creature;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AI逻辑脚本的顶层抽象
 * @author: saltman155
 * @date: 2019/5/1 20:43
 */
public abstract class AbstractAI implements AI3 {

    /**所属对象*/
    private Creature owner;
    /**对象状态*/
    private AIState currentState;
    /**对象子状态*/
    private AISubState currentSubState;
    /**同步锁*/
    private final Lock thinkLock = new ReentrantLock();
    /**黑人问号？？？*/
    private boolean logging = false;
    /**技能id*/
    protected int skillId;
    /**技能等级*/
    protected int skillLevel;

    void onCreatureEvent(AIEventType event, Creature creature);

}
