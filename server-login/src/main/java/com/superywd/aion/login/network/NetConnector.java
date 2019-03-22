package com.superywd.aion.login.network;

import com.superywd.aion.commons.network.NioServer;
import com.superywd.aion.commons.network.ServerConfig;
import com.superywd.aion.login.configs.NetworkConfig;

/**
 * 网络服务启动类
 * @author: 迷宫的中心
 * @date: 2019/3/20 18:00
 */
public class NetConnector {

    /**连接控制管理中心*/
    private static final NioServer instance;

    static{
        // 登录服务器主要需要建立两类连接：
        // 【1】与游戏主逻辑服务器的连接
        // 【2】处理游戏客户端的登录请求连接
        ServerConfig gameServerConfig = new ServerConfig(
                NetworkConfig.loginServerBindAddress,
                NetworkConfig.loginServerPort,
                "游戏主逻辑服务器连接",
                new GameServerConnectionFactory());

        ServerConfig gameClientConfig = new ServerConfig(
                NetworkConfig.loginServerBindAddress,
                NetworkConfig.loginServerPort,
                "游戏客户端连接",
                new GameClientConnectionFactory());

        //建立包含这两个通道的连接管理中心
        instance = new NioServer(NetworkConfig.readWriteThreadCount,gameServerConfig,gameClientConfig);

    }

    public static NioServer getInstance(){
        return instance;
    }
}
