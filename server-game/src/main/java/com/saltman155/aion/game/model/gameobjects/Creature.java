package com.saltman155.aion.game.model.gameobjects;

import com.saltman155.aion.game.model.engine.ai.status.container.CreatureGameStats;
import com.saltman155.aion.game.model.engine.ai.status.container.CreatureLifeStats;
import com.saltman155.aion.game.model.engine.ai.AI;
import com.saltman155.aion.game.model.templates.VisibleObjectTemplate;
import com.saltman155.aion.game.model.templates.spawns.SpawnTemplate;
import com.saltman155.aion.game.word.WorldPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类表示游戏中所有活动对象（可移动的对象）的基类
 * @author: saltman155
 * @date: 2019/5/1 22:19
 */
public class Creature extends VisibleObject {

    private static final Logger log = LoggerFactory.getLogger(Creature.class);

    /**该对象使用的AI*/
    protected AI ai;
    /**该对象即将消亡标记*/
    protected boolean isDespawnDelayed = false;

    /**该对象当前的生命值等属性*/
    private CreatureLifeStats<? extends Creature> lifeStats;
    /**该对象当前的各类游戏属性*/
    private CreatureGameStats<? extends Creature> gameStats;


    public Creature(Integer objectId, VisibleObjectTemplate objectTemplate, SpawnTemplate spawn, WorldPosition position) {
        super(objectId, objectTemplate, spawn, position);
    }
}
