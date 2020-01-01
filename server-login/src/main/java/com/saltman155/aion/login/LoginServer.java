package com.saltman155.aion.login;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.saltman155.aion.login.network.ClientNetConnector;
import com.saltman155.aion.login.network.GameNetConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.security.GeneralSecurityException;

/**
 * 登录服务器启动类
 * @author: saltman155
 * @date: 2018/11/3 23:36
 */

@SpringBootApplication
public class LoginServer {

    private static final Logger logger = LoggerFactory.getLogger(LoginServer.class);

    public static void main(final String[] args) {
        SpringContext.setContext(SpringApplication.run(LoginServer.class, args));
        //载入游戏主服务器配置
        SpringContext.getContext().getBean(MainServerManager.class).init();
        try {
            //开启与游戏主逻辑服务器的网络通信服务
            SpringContext.getContext().getBean(GameNetConnector.class).start();
            //开启与客户端的网络通信服务
            SpringContext.getContext().getBean(ClientNetConnector.class).start();
        } catch (Exception e) {
            logger.error("网络连接打开失败！",e);
            throw new Error(e);
        }
    }


}
