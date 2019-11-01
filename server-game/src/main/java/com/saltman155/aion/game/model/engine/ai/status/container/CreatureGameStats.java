package com.saltman155.aion.game.model.engine.ai.status.container;

import com.saltman155.aion.game.model.gameobjects.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该Creature所属类型的属性
 * @author saltman155
 * @date 2019/10/26 18:12
 */

public abstract class CreatureGameStats<T extends Creature> {

    protected static final Logger logger  = LoggerFactory.getLogger(CreatureGameStats.class);
}
