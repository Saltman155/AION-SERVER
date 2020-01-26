package com.aionstar.game.network.loginserver;

import com.aionstar.commons.utils.ChannelUtil;
import com.aionstar.game.network.loginserver.serverpackets.SM_ACCOUNT_AUTH;
import io.netty.channel.Channel;
import io.netty.channel.internal.ChannelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
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

    /**该映射表保存着该连接服务器上已经连接的用户及连接对象*/
    private final Map<Integer, Channel> loggedChannelMap = new ConcurrentHashMap<>();

    /**该映射表保存着该主服务器上等待验证登录服务器口令的账号*/
    private final Map<Integer,Channel> loginRequests = new HashMap<>();

    private Channel loginServerChannel;

    /**
     * 向登录服务器验证某个用户的各个凭证是否是有效的
     * @param accountId         用户账号
     * @param channel           用户连接
     * @param loginSession      登录凭证
     * @param playSession1      play凭证
     * @param playSession2      play凭证
     */
    public void clientCheckOfLoginServerAuthKey(int accountId,Channel channel,int loginSession,int playSession1,int playSession2){
        if(loginServerChannel == null || !loginServerChannel.isActive()){
            logger.warn("登录服务器都翻车了，还连个锤子连？！");
            //和客户端断开连接
            ChannelUtil.close(channel,null);
            return;
        }
        //同步一下，做个判断
        synchronized (loginRequests){
            //如果已经存在，说明上一个询问还没处理完，直接返回
            if(loginRequests.containsKey(accountId)){ return; }
            loginRequests.put(accountId,channel);
        }
        //否则就记录这个用户，然后发包到登录服务器去问一下
        loginServerChannel.writeAndFlush(new SM_ACCOUNT_AUTH(accountId,loginSession,playSession1,playSession2));

    }

    private static class SingletonHolder{
        private static final LSManager lsManager = new LSManager();
    }

    public static LSManager getInstance(){
        return SingletonHolder.lsManager;
    }

}
