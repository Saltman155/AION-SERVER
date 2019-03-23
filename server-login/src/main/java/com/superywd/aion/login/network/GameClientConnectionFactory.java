package com.superywd.aion.login.network;

import com.superywd.aion.commons.network.AConnection;
import com.superywd.aion.commons.network.AConnectionFactory;
import com.superywd.aion.commons.network.dispatcher.Dispatcher;
import com.superywd.aion.login.configs.CommonsConfig;
import com.superywd.aion.login.network.aion.LoginConnection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * 用于创建连接到游戏客户端的连接对象的工厂类
 * @author: 迷宫的中心
 * @date: 2019/3/22 19:16
 */
public class GameClientConnectionFactory implements AConnectionFactory {

    @Override
    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
        //异常登录保护器开启
        if(CommonsConfig.LOGIN_FLOOD_PROTECTOR){
            //TODO: 异常登录保护器尚在开发
        }
        return new LoginConnection(socket,dispatcher);
    }
}
