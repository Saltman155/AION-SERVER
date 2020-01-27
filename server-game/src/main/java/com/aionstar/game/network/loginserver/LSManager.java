package com.aionstar.game.network.loginserver;

import com.aionstar.commons.utils.ChannelUtil;
import com.aionstar.game.model.account.Account;
import com.aionstar.game.model.account.AccountTime;
import com.aionstar.game.network.client.ClientChannelAttr;
import com.aionstar.game.network.client.serverpackets.SM_L2AUTH_LOGIN_CHECK;
import com.aionstar.game.network.loginserver.serverpackets.SM_ACCOUNT_AUTH;
import com.aionstar.game.service.AccountService;
import io.netty.channel.Channel;
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

    /**该映射表保存着该主服务器上等待验证登录服务器口令的账号*/
    private final Map<Integer,Channel> loginRequests = new HashMap<>();

    /**该映射表保存着该连接服务器上已经连接的用户及连接对象*/
    private final Map<Integer, Channel> authedChannelMap = new HashMap<>();

    /**与登录服务端的连接*/
    private Channel loginServerChannel;

    public void loginServerRegister(Channel channel){
        if(loginServerChannel != null && loginServerChannel.isActive()){
            throw new RuntimeException("注册失败！登录服务器仍在连接中！");
        }
        logger.info("登录服务器通道注册成功.");
        loginServerChannel = channel;
    }

    public void loginServerUnregister(){
        loginServerChannel.close();
        synchronized (loginRequests){
            //关闭所有的待验证的客户端连接
            for(Channel clientChannel : loginRequests.values()){
                ChannelUtil.close(clientChannel,null);
            }
            loginRequests.clear();
        }
        logger.info("登录服务器通道解除注册.");
    }

    /**
     * 向登录服务器验证某个用户的各个凭证是否是有效的
     * @param accountId         用户账号
     * @param channel           用户连接
     * @param loginSession      登录凭证
     * @param playSession1      play凭证
     * @param playSession2      play凭证
     */
    public void clientRequestOfLoginServerAuthKey(int accountId,Channel channel,int loginSession,int playSession1,int playSession2){
        if(loginServerChannel == null || !loginServerChannel.isActive() ||
           loginServerChannel.attr(LSChannelAttr.LS_SESSION_STATE).get() != LSChannelAttr.InnerSessionState.AUTHED){
            logger.warn("登录服务器都没连上，还验证个锤子？！");
            //和客户端断开连接
            ChannelUtil.close(channel,null);
            return;
        }
        //同步一下
        synchronized (loginRequests){
            //如果已经存在，说明上一个询问还没处理完，直接返回
            if(loginRequests.containsKey(accountId)){ return; }
            loginRequests.put(accountId,channel);
        }
        //否则就记录这个用户，然后发包到登录服务器去问一下
        loginServerChannel.writeAndFlush(new SM_ACCOUNT_AUTH(accountId,loginSession,playSession1,playSession2));
    }

    /**
     * 登录服务器返回的封包，判断登录凭证是不是正常的
     * @param accountId             用户账号id
     * @param result                结果
     * @param accountName           用户账号
     * @param accountTime           账号各类时间
     * @param accessLevel           账号权限等级
     * @param membership            账号会员等级
     * @param toll                  账号虚拟货币
     */
    public void clientResponseOfLoginServerAuthKey(int accountId, boolean result, String accountName, AccountTime accountTime,
                                                   byte accessLevel, byte membership, long toll){
        Channel clientChannel;
        synchronized (loginRequests){
            clientChannel = loginRequests.remove(accountId);
        }
        if(clientChannel == null){ return; }

        Account account = AccountService.getAccount(accountId,accountName,accountTime,accessLevel,membership,toll);
        //验证成功
        if(result){
            logger.info("账号 {} : {} playSession验证通过.",accountId,accountName );
            //设置成验证通过
            clientChannel.attr(ClientChannelAttr.SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED);
            authedChannelMap.put(accountId, clientChannel);
            clientChannel.attr(ClientChannelAttr.ACCOUNT).set(account);
            clientChannel.writeAndFlush(new SM_L2AUTH_LOGIN_CHECK(true,accountName));
        }
        //验证失败
        else{
            logger.warn("账号 {} : {} playSession验证失败，可能遭到攻击.",accountId,accountName);
            ChannelUtil.close(clientChannel,new SM_L2AUTH_LOGIN_CHECK(false,accountName));
        }

    }

    private static class SingletonHolder{
        private static final LSManager lsManager = new LSManager();
    }

    public static LSManager getInstance(){
        return SingletonHolder.lsManager;
    }

}
