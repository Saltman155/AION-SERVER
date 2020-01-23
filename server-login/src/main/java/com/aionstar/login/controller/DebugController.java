package com.aionstar.login.controller;

import com.aionstar.login.dao.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 调试用控制器
 * @author saltman155
 * @date 2019/10/23 2:42
 */

public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @Resource
    private AccountMapper accountMapper;

    public static void showAllUser(){
    }
}
