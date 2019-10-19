package com.superywd.aion.login.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringManager {

    private static final Logger logger = LoggerFactory.getLogger(SpringManager.class);

    private static ApplicationContext CONTEXT = null;

    public static synchronized void init(String basePackage){
        logger.info("初始化springIOC容器...");
        if(CONTEXT != null){
            logger.warn("spring容器已完成初始化！");
            return;
        }
        //启动springIOC容器，扫描注解以及处理依赖
        CONTEXT = new AnnotationConfigApplicationContext(basePackage);
        logger.info("springIOC容器初始化完毕！");
    }

    public static ApplicationContext getContext(){
        if(CONTEXT == null){ throw new Error("spring容器未完成初始化！"); }
        return CONTEXT;
    }

    public static void destroy(){
        //nothing
    }

}
