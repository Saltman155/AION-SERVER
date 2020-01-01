package com.saltman155.aion.login.service;

import com.saltman155.aion.login.dao.AccountMapper;
import com.saltman155.aion.login.model.entity.Account;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务处理类
 * @author saltman155
 * @date 2019/10/26 1:47
 */

@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    public Account loginCheck(String account, String password) {
        Account user = accountMapper.selectByAccount(account);
        if(user == null || !user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

}
