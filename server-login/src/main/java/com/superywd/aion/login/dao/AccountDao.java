package com.superywd.aion.login.dao;

import com.superywd.aion.login.model.Account;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:08
 */

public abstract class AccountDao {

    /**
     * 根据用户id获取用户
     * @param id    用户账户id
     * @return      用户
     */
    public abstract Account getAccount(Integer id);

    /**
     * 根据用户名称获取用户
     * @param name  用户名称
     * @return      用户
     */
    public abstract Account getAccount(String name);


}
