package com.superywd.aion.word;

import com.superywd.aion.model.gameobjects.VisibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该类表示地图中的区块
 * @author: saltman155
 * @date: 2019/5/1 21:57
 */
public class MapRegion {

    private static final Logger logger = LoggerFactory.getLogger(MapRegion.class);

    /**区块id（非地图id）*/
    private final int regionId;
    /**该区块所属地图的实例*/
    private final WorldMapInstance parent;
    /**周边区块（包含本身）*/
    private volatile MapRegion[] neighbours = new MapRegion[0];
    /**该区块中注册的可见对象*/
    private final Map<Integer, VisibleObject> objects = new HashMap<>();
    /**用户数量*/
    private final AtomicInteger playerCount = new AtomicInteger(0);
    /**区块是否活动*/
    private final AtomicBoolean regionActive = new AtomicBoolean(false);

    /**该区块所包含zone数量*/
    private final int zoneCount;
    /**该区块包含的所有zone*/
    private Map<Integer, TreeSet<ZoneInstance>> zoneMap;


    public MapRegion(int id, WorldMapInstance parent, ZoneInstance[] zones) {
        this.regionId = id;
        this.parent = parent;
        this.zoneCount = zones.length;
        createZoneMap(zones);
        addNeighbourRegion(this);
    }
}
