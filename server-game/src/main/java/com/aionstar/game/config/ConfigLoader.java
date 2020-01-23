package com.aionstar.game.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.aionstar.commons.properties.ConfigurableProcessor;
import com.aionstar.commons.properties.PropertiesUtil;
import com.aionstar.game.config.network.PlayerIPConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    private static final String CUSTOM_PATH = "./config/custom/custom-env.properties";

    public static final String ENVIRONMENT = System.getProperty("environment","dev");

    public static void load() throws IOException {
        load(ENVIRONMENT);
    }

    public static void load(String env) throws IOException {
        if(StringUtils.isBlank(env)){
            env = ENVIRONMENT;
        }
        //载入日志配置
        loadLoggerConfigure(env);

        logger.info("*********************************开始加载各项配置*********************************");

        //载入各项配置
        File mainFile =  new File("./config/main/main.properties");
        File worldFile = new File("./config/main/world.properties");
        File networkFile = new File("./config/network/network.properties");

        List<Properties> properties = new ArrayList<>();
        if(!mainFile.exists()){ logger.warn("main 配置文件缺失！将采用缺省配置.");} else{
            properties.add(PropertiesUtil.loadProperties(mainFile));
            logger.info("main 配置加载完成.");
        }
        if(!worldFile.exists()){ logger.warn("main 配置文件缺失！将采用缺省配置.");} else{
            properties.add(PropertiesUtil.loadProperties(worldFile));
            logger.info("world 配置加载完成.");
        }
        if(!networkFile.exists()){ logger.warn("network 配置文件缺失！将采用缺省配置.");} else{
            properties.add(PropertiesUtil.loadProperties(networkFile));
            logger.info("network 配置加载完成.");
        }
        File environ = new File(CUSTOM_PATH.replace("env",env));
        //自定义的覆盖一下
        if(environ.exists()){
            properties =  PropertiesUtil.overrideProperties(properties,PropertiesUtil.loadProperties(environ));
            logger.info("自定义 {} 配置加载完成.",env);
        }
        ConfigurableProcessor.process(ServerConfigure.class,properties);
        ConfigurableProcessor.process(WorldConfigure.class,properties);
        ConfigurableProcessor.process(NetworkConfigure.class,properties);

        //载入网络配置
        PlayerIPConfig.load();
        logger.info("客户端合法IP登陆范围 配置加载完成.");
        logger.info("*********************************各项配置加载完成*********************************");
    }

    private static void loadLoggerConfigure(String env){
        final String logPath = "./config/logback/logback-env.xml";
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        File configFile = new File(logPath.replace("env",env));
        if(!configFile.exists() || !configFile.isFile()){
            throw new Error("载入日志文件配置失败！请检查日志配置文件是否存在！");
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
}
