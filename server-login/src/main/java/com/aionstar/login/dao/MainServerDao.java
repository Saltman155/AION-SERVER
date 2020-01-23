package com.aionstar.login.dao;

import com.aionstar.login.dao.mapper.MainServerMapper;
import com.aionstar.login.model.MainServerInfo;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MainServerDao extends BaseDao implements MainServerMapper {

    public MainServerDao(SqlSessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<MainServerInfo> getAllMainServer() {
        MainServerMapper mapper = getMapperInstance(MainServerMapper.class);
        try {
            return mapper.getAllMainServer();
        } catch (Exception e) {
            rollback();
            return null;
        } finally {
            commit();
        }
    }

    @Override
    public MainServerInfo getMainServerById(byte id) {
        MainServerMapper mapper = getMapperInstance(MainServerMapper.class);
        try {
            return mapper.getMainServerById(id);
        } catch (Exception e) {
            rollback();
            return null;
        } finally {
            commit();
        }
    }
}
