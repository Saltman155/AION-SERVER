package com.superywd.aion.login.configs.database;

import com.superywd.aion.login.configs.DataBaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/17 00:41
 */

public class HikariDataSourceFactory extends UnpooledDataSourceFactory {

    /**
     * 自定义Hikari连接池
     */
    public HikariDataSourceFactory() {
        //配置类
        HikariConfig config = new HikariConfig();
        //连接地址
        config.setJdbcUrl(DataBaseConfig.DATABASE_URL);
        //登录名称
        config.setUsername(DataBaseConfig.DATABASE_USER);
        //登录密码
        config.setPassword(DataBaseConfig.DATABASE_PASSWORD);
        //然后赋值
        this.dataSource = new HikariDataSource(config);
    }
}
