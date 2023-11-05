package com.zhouyf.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zhouyf.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement  // 启用基于注解的事务管理
@ComponentScan(basePackageClasses = UserCreateService.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {BookServiceImpl.class, MessageServiceImpl.class, BlogServiceImpl.class,
                        BlogServicePhantomRead.class, BlogServiceTxSpmpl.class,
                BlogServiceDirtyRead.class, BlogServiceTxImpl.class}))
public class TransactionConfig {
    @Bean
    public DataSourceTransactionManager transactionManager(HikariDataSource source) {
        return new DataSourceTransactionManager(source);
    }
}
