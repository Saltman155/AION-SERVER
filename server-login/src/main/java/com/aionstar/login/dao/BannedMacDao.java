package com.aionstar.login.dao;

import com.aionstar.login.dao.mapper.BannedMacMapper;
import com.aionstar.login.dao.mapper.MainServerMapper;
import com.aionstar.login.model.BannedMacEntry;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BannedMacDao extends BaseDao implements BannedMacMapper {

    public BannedMacDao(SqlSessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<BannedMacEntry> selectAll() {
        BannedMacMapper mapper = getMapperInstance(BannedMacMapper.class);
        try {
            return mapper.selectAll();
        } catch (Exception e) {
            rollback();
            return null;
        } finally {
            commit();
        }
    }
}
