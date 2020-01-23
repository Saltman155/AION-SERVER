package com.aionstar.login.config.database;

import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author saltman155
 * @date 2020/1/23 0:36
 */

public class CustomDataSource implements DataSourceFactory {

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public DataSource getDataSource() {
        return null;
    }
}
