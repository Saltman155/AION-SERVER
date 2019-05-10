package com.superywd.aion.model.engine.ai3;

import com.superywd.aion.commons.callbacks.metadata.ObjectCallback;
import com.superywd.aion.model.engine.ai3.callback.OnHandleAIGeneralEvent;
import com.superywd.aion.model.engine.ai3.event.AIEventQueue;
import com.superywd.aion.model.engine.ai3.event.AIEventType;
import com.superywd.aion.model.engine.ai3.event.AIState;
import com.superywd.aion.model.engine.ai3.event.AISubState;
import com.superywd.aion.model.engine.ai3.metadata.AIName;
import com.superywd.aion.model.gameobjects.Creature;
import com.superywd.aion.model.gameobjects.VisibleObject;
import com.superywd.aion.model.gameobjects.player.Player;
import com.superywd.aion.model.templates.npc.shout.ShoutEventType;
import com.superywd.aion.word.WorldPosition;

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
    /**技能id*/
    protected int skillId;
    /**技能等级*/
    protected int skillLevel;
    /**AI事件队列*/
    private volatile AIEventQueue eventQueue;

    AbstractAI() {
        this.currentState = AIState.CREATED;
        this.currentSubState = AISubState.NONE;
    }

    @Override
    public void onGeneralEvent(AIEventType event) {
        if (canHandleEvent(event)) {
            handleGeneralEvent(event);
        }
    }

    @Override
    public void onCreatureEvent(AIEventType event, Creature creature){
        if (canHandleEvent(event)) {
            handleCreatureEvent(event,creature);
        }
    }

    @Override
    public void onCustomEvent(int eventId, Object... args) {
        handleCustomEvent(eventId, args);
    }

    @Override
    public AIState getState() {
        return currentState;
    }

    @Override
    public String getName() {
        //返回该AI脚本在注解中声明的名字
        if (getClass().isAnnotationPresent(AIName.class)) {
            AIName annotation = getClass().getAnnotation(AIName.class);
            return annotation.name();
        }
        return "noname";
    }

    public AIEventQueue getEventQueue() {
        return eventQueue;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public Creature getOwner() {
        return owner;
    }

    /**获取对象ID*/
    public int getObjectId() {
        return owner.getObjectId();
    }

    /**获取对象位置*/
    public WorldPosition getPosition() {
        return owner.getPosition();
    }

    /**获取对象选中的对象*/
    public VisibleObject getTarget() {
        return owner.getTarget();
    }

    /**判断对象是否已经死亡*/
    public boolean isAlreadyDead() {
        return owner.getLifeStats().isAlreadyDead();
    }

    /**设置AI对象*/
    void setOwner(Creature owner) {
        this.owner = owner;
    }

    /**状态修改同步锁上锁*/
    public final boolean tryLockThink() {
        return thinkLock.tryLock();
    }

    /**状态修改同步锁解锁*/
    public final void unlockThink() {
        thinkLock.unlock();
    }

    /**
     * 判断某状态是不是当前的AI状态
     * @param state         待判断状态
     * @return              是否是当前状态
     */
    public final boolean isCurrentState(AIState state){
        return this.currentState == state;
    }

    /**
     * 根据当前状态判断是否能处理指定事件
     * @param eventType     待处理的下一个状态
     * @return              是否能处理
     */
    protected boolean canHandleEvent(AIEventType eventType) {
        switch (this.currentState) {
            case DESPAWNED:
                return StateEvents.DESPAWN_EVENTS.hasEvent(eventType);
            case DIED:
                return StateEvents.DEAD_EVENTS.hasEvent(eventType);
            case CREATED:
                return StateEvents.CREATED_EVENTS.hasEvent(eventType);
        }
        switch (eventType) {
            case DIALOG_START:
            case DIALOG_FINISH:
                return isNonFightingState();
            case CREATURE_MOVED:
                return getName().equals("trap") || currentState != AIState.FIGHT && isNonFightingState();
        }
        return true;
    }

    /**
     * 判断当前是否处于非战斗的状态
     * @return              判断结果
     */
    public boolean isNonFightingState() {
        //行走状态或空闲状态即非战斗状态
        return currentState == AIState.WALKING || currentState == AIState.IDLE;
    }

    /**
     * 设置AI当前状态
     * @param newState      新的状态
     * @return              设置结果
     */
    public synchronized boolean setStateIfNot(AIState newState) {
        if (this.currentState == newState) {
            return false;
        }
        this.currentState = newState;
        return true;
    }

    /**
     * 设置AI当前子状态
     * @param newSubState   新的状态
     * @return              设置结果
     */
    public synchronized boolean setSubStateIfNot(AISubState newSubState) {
        if (this.currentSubState == newSubState) {
            return false;
        }
        this.currentSubState = newSubState;
        return true;
    }

    /**
     * 处理一般事件
     * @param event         具体事件
     */
    @ObjectCallback(OnHandleAIGeneralEvent.class)
    protected void handleGeneralEvent(AIEventType event){
        switch (event) {
            case MOVE_VALIDATE: handleMoveValidate();break;
            case MOVE_ARRIVED: handleMoveArrived();break;
            case SPAWNED: handleSpawned();break;
            case RESPAWNED:handleRespawned();break;
            case DESPAWNED:handleDespawned();break;
            case DIED:handleDied();break;
            case ATTACK_COMPLETE:handleAttackComplete();break;
            case ATTACK_FINISH:handleFinishAttack();break;
            case TARGET_REACHED:handleTargetReached();break;
            case TARGET_TOOFAR:handleTargetTooFar();break;
            case TARGET_GIVEUP:handleTargetGiveUp();break;
            case NOT_AT_HOME:handleNotAtHome();break;
            case BACK_HOME:handleBackHome();break;
            case ACTIVATE:handleActivate();break;
            case DEACTIVATE:handleDeactivate();break;
            //冻结对象事件处理
            case FREEZE:FreezeEventHandler.onFreeze(this);break;
            //对象解冻事件处理
            case UNFREEZE:FreezeEventHandler.onUnfreeze(this);break;
            case DROP_REGISTERED:handleDropRegistered();break;
        }
    }

    /**
     * 处理对象事件
     * @param event         具体事件
     * @param creature      相关对象
     */
    void handleCreatureEvent(AIEventType event, Creature creature) {
        switch (event) {

        }
    }

    /**处理启用对象事件*/
    protected abstract void handleActivate();
    /**处理休眠对象事件*/
    protected abstract void handleDeactivate();
    /**处理刷新对象事件*/
    protected abstract void handleSpawned();
    /**处理对象重刷事件*/
    protected abstract void handleRespawned();
    /**处理对象不再刷新事件*/
    protected abstract void handleDespawned();
    /**处理对象死亡事件*/
    protected abstract void handleDied();
    /**处理移动验证事件*/
    protected abstract void handleMoveValidate();
    /**处理移动抵达事件*/
    protected abstract void handleMoveArrived();
    /**处理攻击完成事件*/
    protected abstract void handleAttackComplete();
    /**处理攻击结束事件*/
    protected abstract void handleFinishAttack();
    /**处理到达目标事件*/
    protected abstract void handleTargetReached();
    /**处理目标过远事件*/
    protected abstract void handleTargetTooFar();
    /**处理放弃目标事件*/
    protected abstract void handleTargetGiveUp();
    /**处理不在刷新点&出生点事件*/
    protected abstract void handleNotAtHome();
    /**处理返回刷新点&出生点事件*/
    protected abstract void handleBackHome();
    /**处理掉落注册事件*/
    protected abstract void handleDropRegistered();
    /**处理发起攻击事件*/
    protected abstract void handleAttack(Creature creature);
    /**处理对象需要帮助事件*/
    protected abstract boolean handleCreatureNeedSupport(Creature creature);
    /**处理警卫攻击事件*/
    protected abstract boolean handleGuardAgainstAttacker(Creature creature);
    /**处理被对象观察事件*/
    protected abstract void handleCreatureSee(Creature creature);
    /**处理不再被对象观察事件*/
    protected abstract void handleCreatureNotSee(Creature creature);
    /**处理其他对象移动事件*/
    protected abstract void handleCreatureMoved(Creature creature);
    /**处理引起其它对象仇恨事件*/
    protected abstract void handleCreatureAggro(Creature creature);
    /**处理观察对象改变事件*/
    protected abstract void handleTargetChanged(Creature creature);
    /**处理跟随其他对象事件*/
    protected abstract void handleFollowMe(Creature creature);
    /**处理取消跟随其他对象事件*/
    protected abstract void handleStopFollowMe(Creature creature);
    /**处理开始与某玩家对话事件*/
    protected abstract void handleDialogStart(Player player);
    /**处理结束与某玩家对话事件*/
    protected abstract void handleDialogFinish(Player player);
    /**处理自定义事件*/
    protected abstract void handleCustomEvent(int eventId, Object... args);
    /**处理放技能时大喊的事件*/
    public abstract boolean onPatternShout(ShoutEventType event, String pattern, int skillNumber);

}
