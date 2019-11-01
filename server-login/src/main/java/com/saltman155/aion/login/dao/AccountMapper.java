package com.saltman155.aion.login.dao;

import com.saltman155.aion.login.model.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:08
 */

@Mapper
public interface AccountMapper {

    /**
     * 根据用户id获取用户
     * @param id        用户账户id
     * @return          用户
     */
    Account selectById(@Param("id") Integer id);

    /**
     * 根据用户名称获取用户
     * @param account   用户账户
     * @return          用户
     */
    Account selectByAccount(@Param("account")String account);


}
