package com.saltman155.aion.game;


import com.saltman155.aion.game.config.spring.SpringContext;
import com.saltman155.aion.game.network.ClientNetConnector;
import com.saltman155.aion.game.network.LoginNetConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameServer {

    private static final Logger logger  = LoggerFactory.getLogger(GameServer.class);

    public static void main( String[] args ) {
        //springboot载入上下文
        SpringContext.setContext(SpringApplication.run(GameServer.class, args));
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
    private static void initServerEnvironment(){

    }

    /**
     * 主服务端网络服务初始化
     * 初始化客户端连接服务，登录服务器连接服务，聊天服务器连接服务
     */
    private static void initServerNetwork(){
        try {
            //启动客户端连接服务
            SpringContext.getContext().getBean(ClientNetConnector.class).start();
            //启动登录服务端连接服务
            SpringContext.getContext().getBean(LoginNetConnector.class).start();
            //聊天服务端先不做...
        } catch (Exception e) {
            logger.error("网络服务无法正常启动！");
            throw new Error(e);
        }
    }
}
