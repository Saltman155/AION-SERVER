package com.saltman155.aion.login;

import org.springframework.context.ApplicationContext;

/**
 * @author saltman155
 * @date 2020/1/1 15:12
 */

public class SpringContext {

    private static ApplicationContext context = null;

    private SpringContext(){}

    public static ApplicationContext getContext() {
        return context;
    }

    static void setContext(ApplicationContext context) {
        SpringContext.context = context;
    }

}
