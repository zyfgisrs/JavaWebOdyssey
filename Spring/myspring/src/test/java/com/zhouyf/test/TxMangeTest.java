package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import com.zhouyf.service.BlogServiceTx;
import com.zhouyf.service.impl.BlogServiceTxImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionConfig.class, HikariDataSourceConfig.class})
public class TxMangeTest {

    @Autowired
    private BlogServiceTxImpl blogServiceTx;

    @Test
    void test(){
        blogServiceTx.add();
    }
}
