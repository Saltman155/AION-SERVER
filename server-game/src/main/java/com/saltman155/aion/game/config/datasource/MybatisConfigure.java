package com.saltman155.aion.game.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: saltman155
 * @date: 2019/3/14 23:02
 */

@Configuration
@MapperScan(basePackages = {"com.saltman155.aion.game.dao"},sqlSessionFactoryRef = "al_server_gs")
public class MybatisConfigure {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfigure.class);

    @Resource(name = "hikariCP")
    private DataSource dataSource;

    @Bean(name = "al_server_gs")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //加载SQL.xml
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("file:./data/mybatis/mappers/*.xml"));
        factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return factoryBean.getObject();
    }

}
