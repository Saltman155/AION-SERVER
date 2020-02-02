package com.aionstar.game.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类用于表示一个对象在游戏世界内的位置
 * @author: saltman155
 * @date: 2019/5/1 21:50
 */
public class WorldPosition {

    private static final Logger logger = LoggerFactory.getLogger(WorldPosition.class);

    /**地图编号*/
    private int mapId;
    /**所属地图块*/
    private MapRegion mapRegion;
    /**x轴坐标*/
    private double x;
    /**y轴坐标*/
    private double y;
    /**z轴坐标*/
    private double z;
    /**朝向角度*/
    private byte heading;
    /**该对象是否生成*/
    private boolean isSpawned = false;

    public int getMapId() {
        if (mapId == 0) {
            logger.warn("地图ID居然是0？！" + this.toString());
        }
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

//    public MapRegion getMapRegion() {
//        return isSpawned ? mapRegion : null;
//    }

//    public int getInstanceId() {
//        return mapRegion.getParent().getInstanceId();
//    }



}
