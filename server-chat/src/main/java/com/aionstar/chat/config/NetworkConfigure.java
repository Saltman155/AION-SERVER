package com.aionstar.chat.config;

import com.aionstar.commons.properties.Property;

public class NetworkConfigure {

    @Property(key="chatserver.network.client.port",defaultValue = "8196")
    public static int CLIENT_PORT;
    @Property(key="chatserver.network.client.boss-thread",defaultValue = "1")
    public static int CLIENT_BOSS_THREAD;
    @Property(key="chatserver.network.client.worker-thread",defaultValue = "3")
    public static int CLIENT_WORKER_THREAD;
    @Property(key="chatserver.network.client.buffer-size",defaultValue = "8196")
    public static int CLIENT_BUFFER_SIZE;


    @Property(key="chatserver.network.main-server.port",defaultValue = "8196")
    public static int MS_PORT;
    @Property(key="chatserver.network.main-server.boss-thread",defaultValue = "1")
    public static int MS_BOSS_THREAD;
    @Property(key="chatserver.network.main-server.worker-thread",defaultValue = "3")
    public static int MS_WORKER_THREAD;
    @Property(key="chatserver.network.main-server.buffer-size",defaultValue = "8196")
    public static int MS_BUFFER_SIZE;

    @Property(key="chatserver.network.main-server.pingpong",defaultValue = "false")
    public static boolean PINGPONG_CHECK;
}
