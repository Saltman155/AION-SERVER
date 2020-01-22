package com.aionstar.game.config.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 * @author: saltman155
 * @date: 2019/3/17 00:41
 */

@Configuration
public class DataSourceConfigure{

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfigure.class);

    @Bean(name="mainServerDataSource")
    @ConfigurationProperties("database.main-server")
    public DataSource mainServerDataSource(){
        logger.info("连接到主服务器数据库...");
        return new HikariDataSource();
    }



}
