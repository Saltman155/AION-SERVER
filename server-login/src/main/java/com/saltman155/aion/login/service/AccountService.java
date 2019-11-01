package com.saltman155.aion.login.service;

import com.saltman155.aion.login.dao.AccountMapper;
import com.saltman155.aion.login.network.client.LoginAuthResponse;
import io.netty.channel.Channel;

/**
 * @author saltman155
 * @date 2019/10/23 2:57
 */

public interface AccountService {



    /**
     * 检查用户登录
     * @param account   用户账号
     * @param password  用户密码
     * @param channel   会话通道
     * @return          检查结果
     */
    LoginAuthResponse userLogin(String account, String password, Channel channel);

}
