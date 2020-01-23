package com.aionstar.login.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.aionstar.commons.properties.ConfigurableProcessor;
import com.aionstar.commons.properties.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 配置载入
 */
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
        File mainFile =  new File("./config/main.properties");
        File networkFile = new File("./config/network.properties");
        if(!mainFile.exists() || !networkFile.exists()){
            logger.warn("配置文件缺失! 将采用缺省配置.");
        }else{
            List<Properties> properties = new ArrayList<>();
            properties.add(PropertiesUtil.loadProperties(mainFile));
            properties.add(PropertiesUtil.loadProperties(networkFile));
            File environ = new File(CUSTOM_PATH.replace("env",env));
            //自定义的覆盖一下
            if(environ.exists()){
                properties =  PropertiesUtil.overrideProperties(properties,PropertiesUtil.loadProperties(environ));
            }
            ConfigurableProcessor.process(MainConfigure.class,properties);
            ConfigurableProcessor.process(NetworkConfigure.class,properties);
        }
        loadLoggerConfigure(env);
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
