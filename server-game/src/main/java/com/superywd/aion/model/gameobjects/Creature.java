package com.superywd.aion.model.gameobjects;

import com.superywd.aion.model.engine.ai3.AI3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类表示游戏中所有生物（可移动的对象）的基类
 * @author: saltman155
 * @date: 2019/5/1 22:19
 */
public class Creature extends VisibleObject {

    private static final Logger log = LoggerFactory.getLogger(Creature.class);

    protected AI3 ai3;
    /***/
    private boolean isDespawnDelayed = false;

}
