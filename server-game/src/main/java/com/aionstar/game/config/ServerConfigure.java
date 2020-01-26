package com.aionstar.game.config;

import com.aionstar.commons.properties.Property;

public class ServerConfigure {

    @Property(key = "server.country.code",defaultValue = "5")
    public static int SERVER_COUNTRY_CODE;

    @Property(key = "server.max-online-players",defaultValue = "300")
    public static int MAX_ONLINE_PLAYERS;

}
