package com.saltman155.aion.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 聊天服务器网络配置
 * @author saltman155
 * @date 2020/1/5 1:36
 */

@Component
@ConfigurationProperties(prefix = "main-server.network.chat")
public class ChatNetwork {
}
