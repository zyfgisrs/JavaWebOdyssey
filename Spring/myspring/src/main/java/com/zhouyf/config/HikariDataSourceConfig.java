package com.zhouyf.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zhouyf.service.impl.BlogServiceImpl;
import com.zhouyf.service.impl.BlogServiceTxImpl;
import com.zhouyf.service.impl.BookServiceImpl;
import com.zhouyf.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:/datasource.properties")
public class HikariDataSourceConfig {
    @Value("${zyf.database.driverClassName}")
    private String driverClassName;
    @Value("${zyf.database.jdbcUrl}")
    private String jdbcUrl;
    @Value("${zyf.database.username}")
    private String username;
    @Value("${zyf.database.password}")
    private String password;
    @Value("${zyf.database.readOnly}")
    private Boolean readOnly;
    @Value("${zyf.database.connectionTimeOut}")
    private long connectionTimeOut;
    @Value("${zyf.database.pool.idleTimeOut}")
    private long idleTimeOut;
    @Value("${zyf.database.pool.maxLifetime}")
    private long maxLifetime;
    @Value("${zyf.database.pool.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${zyf.database.pool.minimumIdle}")
    private int minimumIdle;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setJdbcUrl(this.jdbcUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setReadOnly(this.readOnly);
        dataSource.setConnectionTimeout(this.connectionTimeOut);
        dataSource.setIdleTimeout(this.idleTimeOut);
        dataSource.setMaxLifetime(this.maxLifetime);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setMinimumIdle(this.minimumIdle);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(hikariDataSource());
    }
}
