package com.aionstar.chat;

import com.aionstar.chat.config.NetworkConfigure;
import com.aionstar.chat.network.ClientNetConnector;
import com.aionstar.commons.properties.ConfigurableProcessor;
import com.aionstar.commons.properties.PropertiesUtil;
import com.aionstar.login.config.MainConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Hello world!
 */

public class ChatServer {

    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    public static void main( String[] args ) throws Exception {

        File mainFile =  new File("./config/main.properties");
        File networkFile = new File("./config/network.properties");
        if(!mainFile.exists() || !networkFile.exists()){
            logger.warn("配置文件缺失! 将采用缺省配置.");
        }else{
            List<Properties> properties = new ArrayList<>();
            properties.add(PropertiesUtil.loadProperties(mainFile));
            properties.add(PropertiesUtil.loadProperties(networkFile));
            ConfigurableProcessor.process(MainConfigure.class,properties);
            ConfigurableProcessor.process(NetworkConfigure.class,properties);
        }

        ClientNetConnector.open();
    }

}
