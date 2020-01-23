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
        AccountMapper mapper = getMapperInstance(AccountMapper.class);
        try {
            return mapper.selectById(id);
        } catch (Exception e) {
            rollback();
            return null;
        } finally {
            commit();
        }
    }

    @Override
    public Account selectByAccount(String account) {
        AccountMapper mapper = getMapperInstance(AccountMapper.class);
        try {
            return mapper.selectByAccount(account);
        } catch (Exception e) {
            rollback();
            return null;
        } finally {
            commit();
        }
    }
}
