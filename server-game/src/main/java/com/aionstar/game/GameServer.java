package com.aionstar.game;


import com.aionstar.game.config.ConfigLoader;
import com.aionstar.game.network.ClientNetConnector;
import com.aionstar.game.network.LoginNetConnector;
import com.aionstar.game.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GameServer {

    private static final Logger logger  = LoggerFactory.getLogger(GameServer.class);


    public static void main( String[] args )throws Exception {
        //初始化游戏服务器环境
        initServerEnvironment();
        //初始化网络连接服务环境
        initServerNetwork();
    }

    /**
     * 游戏服务器环境初始化
     * 该方法用于初始化一些基本的与游戏逻辑无关的环境设置，如：
     *  游戏日志记录
     *  数据库连接
     *  服务器线程池
     *  ...
     */
    private static void initServerEnvironment() throws IOException {
        //加载一堆配置
        ConfigLoader.load();
    }

    /**
     * 主服务端网络服务初始化
     * 初始化客户端连接服务，登录服务器连接服务，聊天服务器连接服务
     */
    private static void initServerNetwork(){
        try {
            LoggerUtil.simpleShow("*********************************开始启动网络服务*********************************");
            //启动客户端连接服务
            ClientNetConnector.open();
            //启动登录服务端连接服务
            LoginNetConnector.open();
            //聊天服务端先不做...
            LoggerUtil.simpleShow("*********************************网络服务启动完成*********************************\n");
        } catch (Exception e) {
            logger.error("网络服务无法正常启动！");
            throw new Error(e);
        }
    }
}
