package com.aionstar.login.dao;

import com.aionstar.commons.datasource.BaseDao;
import com.aionstar.login.dao.mapper.BannedMacMapper;
import com.aionstar.commons.network.model.BannedMacEntry;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BannedMacDao extends BaseDao implements BannedMacMapper {

    public BannedMacDao(SqlSessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<BannedMacEntry> selectAll() {
        try {
            BannedMacMapper mapper = getMapperInstance(BannedMacMapper.class);
            return mapper.selectAll();
        } finally {
            close();
        }
    }
}
