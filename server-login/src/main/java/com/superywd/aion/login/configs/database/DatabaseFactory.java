package com.superywd.aion.login.configs.database;

import com.superywd.aion.login.configs.DataBaseConfig;
import com.zaxxer.hikari.pool.HikariPool;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/14 23:02
 */

public class DatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseFactory.class);

    private static SqlSessionFactory sqlSessionFactory;

    public synchronized static void init() {
        if(sqlSessionFactory != null){
            return;
        }
        try {
            //从文件中加载sqlSessionFactory
            InputStream inputStream = Resources.getUrlAsStream(DataBaseConfig.MYBATIS_CONFIG_PATH);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            logger.error("mybatis加载错误！");
            throw new Error(e);
        }
    }


}
