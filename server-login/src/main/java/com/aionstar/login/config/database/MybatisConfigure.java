package com.aionstar.login.config.database;

import com.aionstar.login.model.configure.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author: saltman155
 * @date: 2019/3/14 23:02
 */

@Configuration
@MapperScan(basePackages = {"com.aionstar.login.dao"},sqlSessionFactoryRef = "login_server_factory")
public class MybatisConfigure {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfigure.class);

    private final Database database;

    public MybatisConfigure(Database database) {
        this.database = database;
    }

    @Bean(name = "hikariCP")
    public DataSource buildDataSource() {
        logger.info("准备构建数据源...");
        logger.info("jdbcUrl: {}", database.getJdbcUrl());
        logger.info("driver: {}", database.getDriver());
        logger.info("username: {}", database.getUsername());
        logger.info("password: {}", database.getPassword());
        //配置类
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(database.getJdbcUrl());
        config.setDriverClassName(database.getDriver());
        config.setUsername(database.getUsername());
        config.setPassword(database.getPassword());
        DataSource target = new HikariDataSource(config);
        logger.info("数据源创建完成！");
        //然后创建对象
        return target;
    }

    @Bean(name = "login_server_factory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariCP") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //加载SQL.xml
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("file:./data/mybatis/mappers/*.xml"));
        factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return factoryBean.getObject();
    }

}
