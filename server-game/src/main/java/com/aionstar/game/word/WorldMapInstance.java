package com.aionstar.game.word;

import com.aionstar.game.model.gameobjects.VisibleObject;
import com.aionstar.game.model.gameobjects.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 地图实例类
 * 一个游戏地图实际上会有多个实例以分担玩家的压力，该类用于描述指定的一个实例
 * @author: saltman155
 * @date: 2019/5/5 21:11
 */
public abstract class WorldMapInstance {

    private static final Logger logger = LoggerFactory.getLogger(WorldMapInstance.class);

    /**区块数量*/
    public static final int regionSize = WorldConfig.WORLD_REGION_SIZE;
    /**此实例id*/
    public int instanceId;
    /**该地图实例所属的地图*/
    private final WorldMap parent;
    /**活动的区块*/
    private final Map<Integer,MapRegion> activeRegions = new HashMap<>();
    /**该地图上的活动对象*/
    private final Map<Integer, VisibleObject> wordMapObjects = new HashMap<>();
    /**该地图上的玩家*/
    private final Map<Integer, Player> worldMapPlayers = new HashMap<>();


}
