package com.superywd.aion.login.configs.database;

import com.superywd.aion.login.configs.CommonsConfig;
import com.superywd.aion.login.configs.DataBaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/14 23:02
 */

public class DatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseFactory.class);

    private static HikariPool connectionPool;

    public synchronized static void init() {
        if(connectionPool != null){
            return;
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DataBaseConfig.DATABASE_URL);
        config.setUsername(DataBaseConfig.DATABASE_USER);
        config.setPassword(DataBaseConfig.DATABASE_PASSWORD);
        connectionPool = new HikariPool(config);
    }

    public static void main(String[] args) {
        CommonsConfig.load();
        init();

    }

}
