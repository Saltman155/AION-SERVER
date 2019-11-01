package com.saltman155.aion.login.controller;

import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.service.AccountService;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 用户登录控制器
 * @author saltman155
 * @date 2019/10/20 21:17
 */

@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountBannedController accountBannedController;
    @Resource
    private AccountService accountService;

    public LoginAuthResponse userLogin(String account, String password, Channel channel){
        try {
            //检查ip是不是凉了
            if(accountBannedController.ipIsBanned(ChannelUtil.getIp(channel))){
                return LoginAuthResponse.BAN_IP;
            }
            return accountService.userLogin(account, password, channel);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return LoginAuthResponse.SYSTEM_ERROR;
        }
    }

}
