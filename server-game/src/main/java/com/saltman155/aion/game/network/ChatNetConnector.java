package com.saltman155.aion.game.network;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 与聊天服务的网络连接处理启动类
 * @author saltman155
 * @date 2019/10/24 1:22
 */

@Component
@PropertySource(value = {"file:./config/network/network.properties"})
public class ChatNetConnector {

}
