package com.superywd.aion.login.network;

import com.superywd.aion.commons.network.AConnection;
import com.superywd.aion.commons.network.AConnectionFactory;
import com.superywd.aion.commons.network.dispatcher.Dispatcher;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * 用于创建与游戏主逻辑服务器的连接
 * @author: 迷宫的中心
 * @date: 2019/3/22 19:11
 */
public class GameServerConnectionFactory implements AConnectionFactory {

    @Override
    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
        return null;
    }
}
