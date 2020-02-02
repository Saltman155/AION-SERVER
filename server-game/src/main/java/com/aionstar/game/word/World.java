package com.aionstar.game.word;

import com.aionstar.game.model.gameobjects.VisibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * world对象维护各类在这个世界里的对象的生成，消亡。此外它还管理 worldMap以及它的实例
 * @author saltman155
 * @date 2020/1/31 16:42
 */

public class World {

    private static final Logger logger = LoggerFactory.getLogger(World.class);

    /**存放所有该世界中的玩家*/
    private final PlayerContainer allPlayers;

    /** 世界中所有的可见对象*/
    private final Map<Integer, VisibleObject> allObjects;

    /** 世界中的npc*/
    private final FastMap<Integer, Npc> allNpc;

    /**所有地图*/
    private final Map<Integer,WorldMap> worldMapMaps;
}
