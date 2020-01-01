package com.saltman155.aion.login.controller;

import com.saltman155.aion.login.MainServerManager;
import com.saltman155.aion.login.model.MainServerInfo;
import com.saltman155.aion.login.model.entity.Account;
import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.network.client.serverpackets.SM_SERVER_LIST;
import com.saltman155.aion.login.network.gameserver.loginpackets.request.SM_CHARACTER_REQUEST;
import com.saltman155.aion.login.service.AccountService;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户登录控制器
 * @author saltman155
 * @date 2019/10/20 21:17
 */

@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**当前在登陆服务器上登陆的账号的连接*/
    private final Map<Integer,Channel> accountConnMap = new ConcurrentHashMap<>();
    /**存储用户账号在每个游戏服务器上的角色数量*/
    private final Map<Integer,Map<Integer,Integer>> gameServerCharacterCounts = new ConcurrentHashMap<>();

    @Resource
    private AccountBannedController accountBannedController;
    @Resource
    private AccountService accountService;
    @Resource
    private MainServerManager mainServerManager;

    /**
     * 用户登录判断
     * @param account       用户账号
     * @param password      用户密码
     * @param channel       登录长连接
     * @return              检查结果
     */
    public synchronized LoginAuthResponse userLogin(String account, String password, Channel channel){
        try {
            //检查ip是不是凉了
            if(accountBannedController.ipIsBanned(ChannelUtil.getIp(channel))){
                return LoginAuthResponse.BAN_IP;
            }
            Account user = accountService.loginCheck(account, password);
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



    public synchronized void loadGameServerCharacters(int accountId){
        //重新统计角色数量
        Map<Integer,Integer> characterCounts = new ConcurrentHashMap<>();
        gameServerCharacterCounts.put(accountId,characterCounts);
        for(MainServerInfo gameServer : mainServerManager.getGameServers()){
            Channel gsc = gameServer.getLoginConnection();
            if(gsc != null && gsc.isActive()) {
                //发一个包查询该账号在主服务器上的角色数量
                gsc.writeAndFlush(new SM_CHARACTER_REQUEST(accountId));
            }else{
                characterCounts.put((int) gameServer.getId(),0);
            }
        }
        //发送游戏服务器列表封包
        sendServerList(accountId);
    }

    /**
     * 发送游戏服务端列表给指定账号
     * @param accountId 账号id
     */
    public void sendServerList(int accountId){
        Channel channel = accountConnMap.get(accountId);
        Map<Integer,Integer> characterCounts = gameServerCharacterCounts.get(accountId);
        if(channel != null && channel.isActive()){
            channel.writeAndFlush(new SM_SERVER_LIST(channel,characterCounts));
        }
    }


    private void loginRegister(final Account user,Channel channel){
        ClientChannelAttr.SessionKey key = new ClientChannelAttr.SessionKey(user.getId());
        //改变连接的登录状态
        channel.attr(ClientChannelAttr.SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED_LOGIN);
        channel.attr(ClientChannelAttr.SESSION_KEY).set(key);
        channel.attr(ClientChannelAttr.ACCOUNT).set(user);
        accountConnMap.put(user.getId(),channel);
        //设置当连接断开时，自动从accountConnMap中移除这个连接
        channel.closeFuture().addListener(future -> accountConnMap.remove(user.getId()));
    }

}
