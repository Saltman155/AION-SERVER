package com.aionstar.login.controller;

import org.springframework.stereotype.Controller;

/**
 * @author saltman155
 * @date 2019/10/20 22:07
 */

@Controller
public class AccountBannedController {

    /**
     * 检查ip是否被禁止登录
     * @param ip        请求登录的ip
     * @return          检查结果
     */
    public boolean ipIsBanned(String ip){
        return false;
    }

    /**
     * 检查mac地址是否被禁止登录
     * @param mac       mac地址
     * @return          检查结果
     */
    public boolean macIsBanned(String mac){
        return false;
    }
}
