package com.saltman155.aion.game;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.saltman155.aion.game.config.spring.SpringContext;
import com.saltman155.aion.game.network.ClientNetConnector;
import com.saltman155.aion.game.network.LoginNetConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GameServer {

    private static final Logger logger  = LoggerFactory.getLogger(GameServer.class);

    /**
     * 初始化日志系统配置
     */
    private static void initLogger(){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        File configFile = new File("./config/slf4j-logback.xml");
        if(!configFile.exists() || !configFile.isFile()){
            throw new Error("载入日志文件配置失败！请检查./config/目录下是否有正常的slf4j-logback.xml文件！");
        }else{
            try {
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(context);
                context.reset();
                configurator.doConfigure(configFile);
                StatusPrinter.printInCaseOfErrorsOrWarnings(context);
            } catch (JoranException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    public static void main( String[] args ) {
        initLogger();
        //初始化spring上下文环境
        SpringContext.init(GameServer.class.getPackage().getName());
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
        } catch (InterruptedException e) {
            logger.error("网络服务无法正常启动！");
            throw new Error(e);
        }
    }
}
