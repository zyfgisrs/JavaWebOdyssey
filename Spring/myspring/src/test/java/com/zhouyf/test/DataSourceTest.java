package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HikariDataSourceConfig.class)
public class DataSourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceTest.class);
    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        LOGGER.info("数据库连接对象:{}", this.dataSource.getConnection());
    }
}
