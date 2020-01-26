package com.aionstar.game.config.datasource;

import com.aionstar.commons.datasource.BaseDao;
import com.aionstar.game.config.ConfigLoader;
import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.dao.PlayerDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {

    private static final Logger logger = LoggerFactory.getLogger(DaoManager.class);

    private static SqlSessionFactory sqlSessionFactory;

    private static Map<Class<? extends BaseDao>,BaseDao> daoMap;

    public synchronized static void init(){
        if(sqlSessionFactory != null){ return; }
        InputStream inputStream = null;
        try {
            //从文件中加载sqlSessionFactory
            inputStream = Resources.getUrlAsStream(NetworkConfigure.MYBATIS_CONFIG_PATH);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, ConfigLoader.ENVIRONMENT);
            loadDaoInstance();
        } catch (Exception e) {
            logger.error("mybatis加载错误！");
            throw new Error(e);
        }finally {
            try {
                if(inputStream!= null){ inputStream.close(); }
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    private static void loadDaoInstance(){
        daoMap = new HashMap<>();
        daoMap.put(PlayerDao.class,new PlayerDao(sqlSessionFactory));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDao(Class<T> clazz){
        return (T) daoMap.get(clazz);
    }
}
