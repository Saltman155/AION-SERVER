package com.aionstar.login.dao;

import com.aionstar.commons.datasource.BaseDao;
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
        try {
            MainServerMapper mapper = getMapperInstance(MainServerMapper.class);
            return mapper.getAllMainServer();
        } finally {
            close();
        }
    }

    @Override
    public MainServerInfo getMainServerById(byte id) {
        try {
            MainServerMapper mapper = getMapperInstance(MainServerMapper.class);
            return mapper.getMainServerById(id);
        } finally {
            close();
        }
    }

}
