package com.superywd.aion;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.superywd.aion.commons.service.CronService;
import com.superywd.aion.model.engine.GameEngine;
import com.superywd.aion.model.engine.ai3.AI3Engine;
import com.superywd.aion.utils.JavaAgentUtil;
import com.superywd.aion.utils.thread.ThreadUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.CountDownLatch;

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
        long start = System.currentTimeMillis();
        initLogger();
        //获取几个游戏模块引擎的实例，并在稍后载入
        GameEngine[] parallelEngines = new GameEngine[]{
                AI3Engine.getInstance()
        };
        //引擎加载阻塞同步锁
        final CountDownLatch progressLatch = new CountDownLatch(parallelEngines.length);
        //初始化游戏服务器环境
        initServerEnvironment();

        long end = System.currentTimeMillis();
        logger.info("AION游戏主逻辑服务器启动完成，共花费 {} 秒",(end-start)/1000);

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
        //设置线程未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());
        //检查是否配置了JavaAgent
        JavaAgentUtil.checkConfigured();
        //定时任务注册中心初始化
        CronService.initSingleton(null);

    }
}
