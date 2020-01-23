package com.aionstar.login.config;

import com.aionstar.commons.properties.Property;

/**
 * 基础配置
 */
public class MainConfigure {

    @Property(key="loginserver.server.auto-create",defaultValue = "false")
    public static boolean AUTO_CREATE;
    @Property(key="loginserver.server.flood-protector",defaultValue = "false")
    public static boolean FLOOD_PROTECTOR;
    @Property(key="loginserver.mybatis-config")
    public static String MYBATIS_CONFIG_PATH;

}
