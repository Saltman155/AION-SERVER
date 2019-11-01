package com.saltman155.aion.login.service.impl;

import com.saltman155.aion.login.dao.AccountMapper;
import com.saltman155.aion.login.model.entity.Account;
import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.service.AccountService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author saltman155
 * @date 2019/10/26 1:47
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public LoginAuthResponse userLogin(String account, String password, Channel channel) {
        Account user = accountMapper.selectByAccount(account);
        if(user == null || !user.getPassword().equals(password)){
            return LoginAuthResponse.INVALID_PASSWORD;
        }
        ClientChannelAttr.SessionKey key = new ClientChannelAttr.SessionKey(user.getId());
        //设置连接的登录状态
        channel.attr(ClientChannelAttr.SESSION_STATE).set(ClientChannelAttr.SessionState.AUTHED_LOGIN);
        channel.attr(ClientChannelAttr.SESSION_KEY).set(key);
        channel.attr(ClientChannelAttr.ACCOUNT_ID).set(user.getId());
        return LoginAuthResponse.AUTHED;
    }


}
