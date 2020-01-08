package com.saltman155.aion.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * NPC服务器网络配置
 * @author saltman155
 * @date 2020/1/5 1:35
 */

@Component
@ConfigurationProperties(prefix = "main-server.network.npc")
public class NpcNetwork {

}
