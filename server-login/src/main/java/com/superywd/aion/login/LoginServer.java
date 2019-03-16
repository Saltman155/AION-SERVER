package com.superywd.aion.login;

import com.superywd.aion.login.configs.ConfigLoad;
import com.superywd.aion.login.configs.database.DatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static void initLogger(){ }

    public static void main(final String[] args){
        long start = System.currentTimeMillis();
        initLogger();
//        CronService.initSingleton(ThreadPoolManagerRunnableRunner.class);
        //载入配置
         ConfigLoad.load();
        //载入数据库
        DatabaseFactory.init();
    }
}
