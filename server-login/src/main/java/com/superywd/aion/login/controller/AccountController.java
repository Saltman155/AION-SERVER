package com.superywd.aion.login.controller;

import com.superywd.aion.login.network.aion.LoginAuthResponse;
import io.netty.channel.Channel;
import org.springframework.stereotype.Controller;

/**
 * 用户登录控制器
 * @author saltman155
 * @date 2019/10/20 21:17
 */

@Controller
public class AccountController {

    public LoginAuthResponse userLogin(String account, String password, Channel channel){
        return LoginAuthResponse.AUTHED;
    }
}
