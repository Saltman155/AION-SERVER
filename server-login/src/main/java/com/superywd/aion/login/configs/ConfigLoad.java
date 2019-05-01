package com.superywd.aion.login.configs;

import com.superywd.aion.commons.properties.ConfigurableProcessor;
import com.superywd.aion.commons.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * @author: saltman155
 * @date: 2019/3/15 23:06
 */

public class ConfigLoad {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoad.class);


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
            //自定义的配置覆盖
            PropertiesUtil.overrideProperties(propertiesList,myLoginServer);
            logger.info("载入登录服务器配置文件配置到服务：" + networkDir + "/commons.properties");
            ConfigurableProcessor.process(CommonsConfig.class,propertiesList);
            logger.info("载入登录服务器配置文件配置到服务：" + networkDir + "/network.properties");
            ConfigurableProcessor.process(NetworkConfig.class,propertiesList);
            logger.info("载入登录服务器配置文件配置到服务：" + networkDir + "/database.properties");
            ConfigurableProcessor.process(DataBaseConfig.class,propertiesList);
        }catch (Exception e){
            logger.error("登录服务器载入配置文件错误！",e);
            throw new Error("登录服务器载入配置文件错误!", e);
        }
    }
}
