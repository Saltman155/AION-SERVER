package com.aionstar.login;

import com.aionstar.login.config.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 登录服务器启动类
 * @author: saltman155
 * @date: 2018/11/3 23:36
 */

@SpringBootApplication
public class LoginServer {

    private static final Logger logger = LoggerFactory.getLogger(LoginServer.class);

    public static void main(final String[] args) {
        SpringContext.setContext(SpringApplication.run(LoginServer.class, args));
    }


}
