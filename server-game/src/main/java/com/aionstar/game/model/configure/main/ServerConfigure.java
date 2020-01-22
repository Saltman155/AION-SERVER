package com.aionstar.game.model.configure.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author saltman155
 * @date 2020/1/18 19:57
 */

@Component
@PropertySource(value = {"file:./config/main/server.properties"})
public class ServerConfigure {

    @Value("${server.max-online-players}")
    private Integer maxOnlinePlayers;

    public Integer getMaxOnlinePlayers() { return maxOnlinePlayers; }
    public void setMaxOnlinePlayers(Integer maxOnlinePlayers) { this.maxOnlinePlayers = maxOnlinePlayers; }

    private static ServerConfigure instance;

    @PostConstruct
    private void buildInstance(){
        ServerConfigure.instance = this;
    }

    public static ServerConfigure getInstance() {
        return instance;
    }
}
