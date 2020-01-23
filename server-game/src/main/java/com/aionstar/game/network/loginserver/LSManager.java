package com.aionstar.game.network.loginserver;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录服务器连接管理中心
 * 该类维护着主服务器与登录服务器连接的一些基本信息
 * @author saltman155
 * @date 2020/1/24 0:44
 */

public class LSManager {

    private static final Logger logger = LoggerFactory.getLogger(LSManager.class);

    private LSManager(){}

    /**
     * 该映射表保存着该连接服务器上已经连接的用户及连接对象
     * key: accountId value: 连接对象
     */
    private Map<Integer, Channel> loggedChannelMap = new ConcurrentHashMap<>();







    private static class SingletonHolder{
        private static final LSManager lsManager = new LSManager();
    }

    public static LSManager getInstance(){
        return SingletonHolder.lsManager;
    }

}
