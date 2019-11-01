package com.saltman155.aion.game.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 数据源配置
 * @author: saltman155
 * @date: 2019/3/17 00:41
 */

@Configuration
@PropertySource(value = {"file:./config/network/datasource.properties"})
public class DataSourceConfigure{

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfigure.class);

    @Value("${database.url}")
    private String jdbcUrl;
    @Value("${database.driver}")
    private String driver;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Bean(name = "hikariCP")
    public DataSource buildDataSource() {
        logger.info("准备创建gameserver数据源...");
        //配置类
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        //然后创建对象
        DataSource target = new HikariDataSource(config);
        logger.info("gameserver数据源创建完成！");
        return target;
    }
}
