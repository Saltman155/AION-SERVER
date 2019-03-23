package com.superywd.aion.login.configs;


import com.superywd.aion.commons.properties.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: 迷宫的中心
 * @date: 2019/3/14 22:24
 */

public class CommonsConfig {

    private static final Logger logger = LoggerFactory.getLogger(CommonsConfig.class);

    @Property(key = "common.runnable.stats.enable",defaultValue = "false")
    public static boolean RUNNABLE_STATS_ENABLE;

    @Property(key = "loginserver.commons.floodprotector",defaultValue = "true")
    public static boolean LOGIN_FLOOD_PROTECTOR;

}
