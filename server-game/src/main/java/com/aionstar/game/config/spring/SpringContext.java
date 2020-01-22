package com.aionstar.game.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


/**
 * spring上下文
 * @author saltman155
 * @date 2019/10/24 0:31
 */

public class SpringContext {

    private static final Logger logger = LoggerFactory.getLogger(SpringContext.class);

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringContext.context = context;
    }
}