package com.superywd.aion.login.network;

import com.superywd.aion.commons.network.NioServer;
import com.superywd.aion.commons.network.ServerConfig;
import com.superywd.aion.login.configs.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 网络服务启动类
 * @author: 迷宫的中心
 * @date: 2019/3/20 18:00
 */
public class NetConnector {

    private static final Logger logger = LoggerFactory.getLogger(NetConnector.class);

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

    public static void start(){
        try {
            instance.OpenConnection();
        } catch (IOException e) {
            logger.error("网络服务器启动失败！");
            logger.error(e.getMessage(),e);
            throw new Error(e);
        }
    }
}
