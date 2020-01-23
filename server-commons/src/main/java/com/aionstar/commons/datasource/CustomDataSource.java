package com.aionstar.commons.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 自定义的数据源配置
 * @author saltman155
 * @date 2020/1/23 0:36
 */

public class CustomDataSource implements DataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(CustomDataSource.class);

    private DataSource dataSource;

    @Override
    public void setProperties(Properties properties) {
        String jdbcUrl = properties.getProperty("datasource.url");
        String driver = properties.getProperty("datasource.driver");
        String userName = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        buildDataSource(jdbcUrl,driver,userName,password);
        logger.info("数据源载入成功.");
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    private void buildDataSource(String jdbcUrl,String driver,String userName,String password){
        if(jdbcUrl == null) { throw new Error("jdbcUrl配置不存在！");  }
        if(userName == null){ throw new Error("username配置不存在！"); }
        if(password == null){ throw new Error("password配置不存在！"); }
        if(driver == null)  { throw new Error("driver配置不存在！");   }
        //这里我用的是hikari连接池，世界上最快的连接池！
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driver);
        config.setUsername(userName);
        config.setPassword(password);
        config.setAutoCommit(true);
        this.dataSource = new HikariDataSource(config);
    }
}
