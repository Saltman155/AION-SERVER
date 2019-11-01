package com.saltman155.aion.game.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * spring上下文
 * @author saltman155
 * @date 2019/10/24 0:31
 */

public class SpringContext {

    private static final Logger logger = LoggerFactory.getLogger(SpringContext.class);

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