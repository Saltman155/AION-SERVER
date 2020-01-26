//package com.superywd.aion.word;
//
//import com.superywd.aion.model.templates.world.WorldMapTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 此类表示游戏中的某一个地图（如普埃塔、贝尔特伦..），
// * 实际上一个地图会分为多个实例 {@link WorldMapInstance}（多线）用于分担玩家
// * @author: saltman155
// * @date: 2019/5/5 21:22
// */
//public class WorldMap {
//
//    /**地图模板配置*/
//    private WorldMapTemplate worldMapTemplate;
//    /***/
//    private AtomicInteger nextInstanceId = new AtomicInteger(0);
//    /**地图实例表*/
//    private Map<Integer, WorldMapInstance> instances = new HashMap<>();
//}
