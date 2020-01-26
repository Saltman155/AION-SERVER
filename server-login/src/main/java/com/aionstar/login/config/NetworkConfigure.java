package com.aionstar.login.config;

import com.aionstar.commons.properties.Property;

/**
 * 网络服务配置
 */

public class NetworkConfigure {

    @Property(key="loginserver.network.client.port",defaultValue = "2106")
    public static int CLIENT_PORT;
    @Property(key="loginserver.network.client.boss-thread",defaultValue = "1")
    public static int CLIENT_BOSS_THREAD;
    @Property(key="loginserver.network.client.worker-thread",defaultValue = "3")
    public static int CLIENT_WORKER_THREAD;
    @Property(key="loginserver.network.client.buffer-size",defaultValue = "8196")
    public static int CLIENT_BUFFER_SIZE;


    @Property(key="loginserver.network.main-server.port",defaultValue = "8196")
    public static int MS_PORT;
    @Property(key="loginserver.network.main-server.boss-thread",defaultValue = "1")
    public static int MS_BOSS_THREAD;
    @Property(key="loginserver.network.main-server.worker-thread",defaultValue = "3")
    public static int MS_WORKER_THREAD;
    @Property(key="loginserver.network.main-server.buffer-size",defaultValue = "8196")
    public static int MS_BUFFER_SIZE;

    @Property(key="loginserver.network.main-server.pingpong",defaultValue = "false")
    public static boolean PINGPONG_CHECK;

}
