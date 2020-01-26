package com.aionstar.game.controller;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author saltman155
 * @date 2020/1/21 0:14
 */

public class NetworkController {

    private static AtomicInteger serverCount = new AtomicInteger();

    /**
     * 更新服务器数量
     * @param count     新的服务器数量
     */
    public static void updateServerCount(int count){
        serverCount.set(count);
    }

    /**
     * 获取服务器数量
     * @return         服务器数量
     */
    public static int getServerCount(){
        return serverCount.get();
    }
}
