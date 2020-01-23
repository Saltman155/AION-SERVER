package com.aionstar.login.controller;

import com.aionstar.login.model.entity.Account;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.LoginAuthResponse;
import com.aionstar.login.service.AccountService;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.network.client.serverpackets.SM_SERVER_LIST;
import com.aionstar.login.network.mainserver.serverpackets.SM_CHARACTER;
import com.aionstar.login.service.MainServerService;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户登录控制器
 * @author saltman155
 * @date 2019/10/20 21:17
 */

public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**当前在登陆服务器上登陆的账号的连接*/
    private static final Map<Integer,Channel> accountConnMap = new ConcurrentHashMap<>();
    /**存储用户账号在每个游戏服务器上的角色数量*/
    private static final Map<Integer,Map<Byte,Integer>> gameServerCharacterCounts = new ConcurrentHashMap<>();

    /**
     * 用户登录判断
     * @param account       用户账号
     * @param password      用户密码
     * @param channel       登录长连接
     * @return              检查结果
     */
    public static synchronized LoginAuthResponse userLogin(String account, String password, Channel channel){
        try {
            //检查ip是不是凉了
            if(AccountBannedController.ipIsBanned(ChannelUtil.getIp(channel))){
                return LoginAuthResponse.BAN_IP;
            }
            Account user = AccountService.loginCheck(account, password);
            if(user == null){
                return LoginAuthResponse.INVALID_PASSWORD;
            }
            //登录成功 一通登记
            loginRegister(user,channel);
            return LoginAuthResponse.AUTHED;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return LoginAuthResponse.SYSTEM_ERROR;
        }
    }


    /**
     * 向所有的游戏服务器请求指定账号在游戏服务器上的角色数量
     * @param account        账号信息
     */
    public static synchronized void loadGameServerCharacters(Account account){
        //重新统计角色数量
        Map<Byte,Integer> countMap = new HashMap<>();
        gameServerCharacterCounts.put(account.getId(),countMap);
        for(MainServerInfo gameServer : MainServerService.getGameServers()){
            Channel gsc = gameServer.getLoginConnection();
            if(gsc != null && gsc.isActive()) {
                // 发一个包查询该账号在主服务器上的角色数量，
                // 稍后主服务器会返回查询结果的封包，再次设置countMap
                gsc.writeAndFlush(new SM_CHARACTER(account.getId()));
            }else{
                //服务器没有存活，就直接存放一个0进去
                countMap.put(gameServer.getId(),0);
            }
        }
        //如果所有主服务器的查询响应都处理完成，则发送服务器列表封包给客户端
        if(allGameServerHandled(account.getId())){
            //发送游戏服务器列表封包
            sendServerList(account);
        }

    }

    /**
     * 发送游戏服务端列表给指定账号
     * @param account 账号id
     */
    public static void sendServerList(Account account){
        int accountId = account.getId();
        Channel channel = accountConnMap.get(accountId);
        Map<Byte,Integer> characterCounts = gameServerCharacterCounts.get(accountId);
        if(channel != null && channel.isActive()){
            channel.writeAndFlush(new SM_SERVER_LIST(account,characterCounts));
        }
    }

    /**
     * 该方法用于判断，登录服务器关于一个账号对所有游戏服务器的查询角色数量都已得到了处理
     * @param accountId     判断的账号
     * @return              是否已经处理完成
     */
    public static synchronized boolean allGameServerHandled(int accountId){
        Map<Byte,Integer> countMap =  gameServerCharacterCounts.get(accountId);
        if(countMap != null){
            // 当countMap中的键值对数量与游戏服务器数量相等时，
            // 说明所有的游戏服务器查询结果都已经设置完毕
            return countMap.size() == MainServerService.getGameServers().size();
        }
        return false;
    }

    /**
     * 更新与客户端的连接状态为验证通过，并绑定账号相关数据到连接上
     * @param user          连接关联的账号
     * @param channel       连接对象
     */
    private static void loginRegister(final Account user,Channel channel){
        //随机生成一个sessionKey
        ClientChannelAttr.SessionKey key = new ClientChannelAttr.SessionKey(user.getId());
        //改变连接的登录状态
        channel.attr(ClientChannelAttr.C_SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED_LOGIN);
        channel.attr(ClientChannelAttr.SESSION_KEY).set(key);
        channel.attr(ClientChannelAttr.ACCOUNT).set(user);
        accountConnMap.put(user.getId(),channel);
        //设置当连接断开时，自动从accountConnMap中移除这个连接
        channel.closeFuture().addListener(future -> accountConnMap.remove(user.getId()));
    }

}
