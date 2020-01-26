package com.aionstar.login;

import com.aionstar.login.config.ConfigLoader;
import com.aionstar.login.config.datasource.DaoManager;
import com.aionstar.login.network.ClientNetConnector;
import com.aionstar.login.network.MSNetConnector;
import com.aionstar.login.network.crypt.LKeyGenerator;
import com.aionstar.login.service.MainServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 登录服务器启动类
 * @author: saltman155
 * @date: 2018/11/3 23:36
 */

public class LoginServer {

    private static final Logger logger = LoggerFactory.getLogger(LoginServer.class);

    static {
        //载入配置
        try {
            ConfigLoader.load();
        } catch (IOException e) {
            throw new Error(e);
        }
    }
    public static void main(final String[] args) throws Exception {
        showLOGO();
        //载入数据库配置
        DaoManager.init();
        //载入游戏主服务器配置
        MainServerService.loadMainServer();
        //初始化密钥对
        LKeyGenerator.init();
        //开启网络服务
        openNetwork();
    }

    private static void openNetwork(){
        try {
            //打开客户端连接服务
            ClientNetConnector.open();
            //打开主服务端连接服务
            MSNetConnector.open();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static void showLOGO(){
        Logger logger = LoggerFactory.getLogger("simpleLogger");
        logger.info("                     ******************************************");
        logger.info("                     *     aion-star  login-sever v1.00       *");
        logger.info("                     ******************************************\n");
    }


}
