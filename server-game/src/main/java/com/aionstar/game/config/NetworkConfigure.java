package com.aionstar.game.config;

import com.aionstar.commons.properties.Property;

public class NetworkConfigure {

    @Property(key = "mainserver.network.client.port")
    public static int CLIENT_PORT;
    @Property(key = "mainserver.network.client.boss-thread")
    public static int CLIENT_BOSS_THREAD;
    @Property(key = "mainserver.network.client.worker-thread")
    public static int CLIENT_WORKER_THREAD;
    @Property(key = "mainserver.network.client.buffer-size")
    public static int CLIENT_BUFFER_SIZE;

    @Property(key = "mainserver.network.login-server.address")
    public static String LS_ADDRESS;
    @Property(key = "mainserver.network.login-server.port")
    public static int LS_PORT;
    @Property(key = "mainserver.network.login-server.thread")
    public static int LS_THREAD;
    @Property(key = "mainserver.network.login-server.buffer-size")
    public static int LS_BUFFER_SIZE;
    @Property(key = "mainserver.network.login-server.register-id")
    public static int LS_REGISTER_ID;
    @Property(key = "mainserver.network.login-server.password")
    public static String LS_PASSWORD;

    @Property(key="loginserver.mybatis-config")
    public static String MYBATIS_CONFIG_PATH;


}
