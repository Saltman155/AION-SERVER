package com.saltman155.aion.game.model.configure.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author saltman155
 * @date 2020/1/5 1:47
 */

@Component
@PropertySource(value = {"file:./config/main/world.properties"})
public class WorldConfigure  {

    @Value("${server.world.region.size}")
    private int regionSize;
    @Value("${server.world.region.active.trace}")
    private boolean activeTrace;

}
