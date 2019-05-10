package com.superywd.aion.configs.main;

import com.superywd.aion.commons.properties.Property;

/**
 * 游戏世界地图大小配置
 * @author: saltman155
 * @date: 2019/5/5 21:14
 */
public class WorldConfig {

    /**一个地图中活动的区块大小*/
    @Property(key = "server.world.region.size", defaultValue = "128")
    public static int WORLD_REGION_SIZE;

    /**跟踪活动区域并禁用非活动区
     * 慎用！如果设置为false,则所有地图区块都会是活动的（需要极高的配置）*/
    @Property(key = "server.world.region.active.trace", defaultValue = "true")
    public static boolean WORLD_ACTIVE_TRACE;


}
