package com.superywd.aion.login;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.superywd.aion.commons.service.CronService;
import com.superywd.aion.login.configs.ConfigLoad;
import com.superywd.aion.login.configs.database.DatabaseFactory;
import com.superywd.aion.login.utils.cron.ThreadPoolManagerRunnableRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 登录服务器启动类
 * @author: 迷宫的中心
 * @date: 2018/11/3 23:36
 */

public class LoginServer {

    private static final Logger logger = LoggerFactory.getLogger(LoginServer.class);

    /**
     * 初始化日志文件配置
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

    public static void main(final String[] args){
        long start = System.currentTimeMillis();
        initLogger();
        //定时任务中心初始化
        CronService.initSingleton(ThreadPoolManagerRunnableRunner.class);
        //载入各类配置
         ConfigLoad.load();
        //载入数据库配置
        DatabaseFactory.init();

    }
}
