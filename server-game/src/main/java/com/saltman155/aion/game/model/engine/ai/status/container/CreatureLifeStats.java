package com.saltman155.aion.game.model.engine.ai.status.container;

import com.saltman155.aion.game.model.gameobjects.Creature;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该类用于描述一个Creature个体在当前时间点的活动状态
 * @author saltman155
 * @date 2019/10/26 17:50
 */

public abstract class CreatureLifeStats<T extends Creature> {

    private static final Logger logger = LoggerFactory.getLogger(CreatureLifeStats.class);

    protected T owner;

    protected int currentHp;
    protected int currentMp;

    protected boolean alreadyDead = false;

    /**HP操作同步锁*/
    private final Lock hpLock = new ReentrantLock();
    /**MP操作同步锁*/
    private final Lock mpLock = new ReentrantLock();
    /**恢复操作同步锁*/
    protected final Lock restoreLock = new ReentrantLock();
    /**恢复操作future*/
    protected volatile Future<?> lifeRestoreTask;

    public CreatureLifeStats(T owner, int currentHp, int currentMp) {
        this.owner = owner;
        this.currentHp = currentHp;
        this.currentMp = currentMp;
    }

    public T getOwner() {
        return owner;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public boolean isAlreadyDead() {
        return alreadyDead;
    }

    /**
     * 每当生命值收到外界影响发生变化时调用此方法
     * @param value         变化值
     * @param attacker      施加影响的外界对象
     * @return              变化后的值
     */
    public int reduceHp(int value, @NotNull Creature attacker){
        boolean isDied = false;
        hpLock.lock();
        try{
            if(!alreadyDead){
                int newHp = this.currentHp - value;
                if(newHp < 0){
                    newHp = 0;
                    this.currentHp = 0;
                    alreadyDead = true;
                    isDied = true;
                }
                this.currentHp = newHp;
            }
        }finally {
            hpLock.unlock();
        }
        if(value != 0){ onReduceHp(); }
        if(isDied){}
        return currentHp;
    }

    /**该方法在hp发生变化后被调用*/
    protected abstract void onReduceHp();

}
