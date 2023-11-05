package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionConfig.class, HikariDataSourceConfig.class})
public class TransactionManagerTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void test(){
        System.out.println(transactionManager);
    }
}
