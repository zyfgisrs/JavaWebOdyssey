package com.zhouyf.service.impl;

import com.zhouyf.service.BlogServiceTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class BlogServiceTxSpmpl implements BlogServiceTx {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add() {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(definition);
        try {
            jdbcTemplate.update(sql, "111", "111");
            jdbcTemplate.update(sql, "222", "111");
            Object savepoint = status.createSavepoint();
            LOGGER.info("设置保存点{}", savepoint);
            try {
                jdbcTemplate.update(sql, "333", "111");
                jdbcTemplate.update(sql, "444", "111");
                jdbcTemplate.update(sql, "111", "111", 1);//设置错误
            } catch (DataAccessException e) {
                LOGGER.info("发生错误，归滚到保存点{}", savepoint);
                status.rollbackToSavepoint(savepoint);
                e.printStackTrace();
            }
            txManager.commit(status);
            LOGGER.info("事务提交");
        } catch (DataAccessException e) {
            txManager.rollback(status);
            LOGGER.info("执行失败，事务回滚");
            throw new RuntimeException(e);
        }
    }
}
