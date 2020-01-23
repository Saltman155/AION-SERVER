package com.aionstar.game.config;

import com.aionstar.commons.properties.Property;

public class ServerConfigure {

    @Property(key = "server.max-online-players",defaultValue = "300")
    public static int MAX_ONLINE_PLAYERS;

}
