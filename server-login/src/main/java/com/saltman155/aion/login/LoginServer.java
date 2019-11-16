package com.saltman155.aion.login;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.saltman155.aion.login.config.spring.SpringContext;
import com.saltman155.aion.login.network.ClientNetConnector;
import com.saltman155.aion.login.network.GameNetConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.GeneralSecurityException;

/**
 * 登录服务器启动类
 * @author: saltman155
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

    public static void main(final String[] args) throws GeneralSecurityException {
        long start = System.currentTimeMillis();
        showLogo();
        //日志初始化
        initLogger();
        //spring容器初始化
        SpringContext.init(LoginServer.class.getPackage().getName());
        //载入游戏主服务器配置
        SpringContext.getContext().getBean(GameServerManager.class).init();
        try {
            //开启与游戏主逻辑服务器的网络通信服务
            SpringContext.getContext().getBean(GameNetConnector.class).start();
            //开启与客户端的网络通信服务
            SpringContext.getContext().getBean(ClientNetConnector.class).start();
        } catch (Exception e) {
            logger.error("网络连接打开失败！",e);
            throw new Error(e);
        }
        long end = System.currentTimeMillis();
        logger.info("登录服务器启动完成！花费 {} 秒",(end - start)/1000.0);
    }



    private static void showLogo(){
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++ AION LIGHTNING 3.9 +++++++++++++");
        System.out.println("+++++++++++++++++++++++LOGIN SERVER++++++++");
    }

}
