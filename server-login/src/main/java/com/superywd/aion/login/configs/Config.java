package com.superywd.aion.login.configs;

import com.superywd.aion.commons.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * 登录服务器各类配置载入
 * @author: 迷宫的中心
 * @date: 2018/11/4 12:51
 */

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    /**账号字符集*/
    public static String accountCharset;

    /**最短登录间隔时间*/
    public static int fastReconnectionTime;

    /**登录服务器端口*/
    public static int loginServerPort;

    /**登录服务器监听地址*/
    public static String loginServerBindAddress;

    /**游戏主体服务器端口*/
    public static int gameServerPort;

    /**游戏主体服务器监听地址*/
    public static String gameServerBindAddress;

    /***/
    public static int loginTryBeforeBan;

    public static int wrongLoginBanTime;


    /**
     * 载入所有的配置文件
     */
    public static void load(){
        String myLoginServerFilePath = "./config/myLoginServer.properties";
        String networkDir = "./config/network";

        try{
            Properties myLoginServer = null;
            try {
                logger.info("载入自定义的配置文件...");
                myLoginServer = PropertiesUtil.loadProperties(myLoginServerFilePath);
            }catch (Exception e){
                logger.info("无自定义的配置文件可以载入...");
            }
            List<Properties> propertiesList = PropertiesUtil.loadAllFromDirectory(networkDir);
            //用自定义的覆盖一下
            PropertiesUtil.overrideProperties(propertiesList,myLoginServer);
            logger.info("载入登录服务器配置文件：" + networkDir + "/network.properties");
            logger.info("载入登录服务器配置文件：" + networkDir + "/commons.properties");
            logger.info("载入登录服务器配置文件：" + networkDir + "/database.properties");
        }catch (Exception e){
            logger.error("登录服务器载入配置文件错误！",e);
            throw new Error("登录服务器载入配置文件错误!", e);
        }
    }


}
