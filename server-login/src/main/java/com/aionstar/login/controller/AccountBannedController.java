package com.aionstar.login.controller;

import com.aionstar.commons.network.model.BannedMacEntry;
import com.aionstar.login.service.AccountBannedService;

import java.util.Map;

/**
 * @author saltman155
 * @date 2019/10/20 22:07
 */


public class AccountBannedController {


    /**
     * 检查ip是否被禁止登录
     * @param ip        请求登录的ip
     * @return          检查结果
     */
    public static boolean ipIsBanned(String ip){
        return false;
    }

    /**
     * 检查mac地址是否被禁止登录
     * @param mac       mac地址
     * @return          检查结果
     */
    public static boolean macIsBanned(String mac){
        return false;
    }


    /**
     * 获取所有被禁止的mac地址
     * @return          返回所有被禁止的mac地址
     */
    public static Map<String, BannedMacEntry> getAllMacBand(){
        return AccountBannedService.getAllMacBand();
    }

}
