package com.aionstar.login.service;

import com.aionstar.login.config.datasource.DaoManager;
import com.aionstar.login.dao.AccountDao;
import com.aionstar.login.model.entity.Account;

/**
 * 用户业务处理类
 * @author saltman155
 * @date 2019/10/26 1:47
 */

public class AccountService {

    public static Account loginCheck(String account, String password) {
        Account user = DaoManager.getDao(AccountDao.class).selectByAccount(account);
        if(user == null || !user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

}
