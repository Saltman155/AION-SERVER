package com.aionstar.login.dao;

import com.aionstar.commons.datasource.BaseDao;
import com.aionstar.login.dao.mapper.AccountMapper;
import com.aionstar.login.model.entity.Account;
import org.apache.ibatis.session.SqlSessionFactory;

public class AccountDao extends BaseDao implements AccountMapper {

    public AccountDao(SqlSessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Account selectById(Integer id) {
        try {
            AccountMapper mapper = getMapperInstance(AccountMapper.class);
            return mapper.selectById(id);
        } finally {
            close();
        }
    }

    @Override
    public Account selectByAccount(String account) {
        try {
            AccountMapper mapper = getMapperInstance(AccountMapper.class);
            return mapper.selectByAccount(account);
        } finally {
            close();
        }
    }
}
