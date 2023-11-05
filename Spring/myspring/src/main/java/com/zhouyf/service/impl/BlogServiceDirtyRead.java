package com.zhouyf.service.impl;

import com.zhouyf.pojo.Post;
import com.zhouyf.service.BlogServiceTxThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class BlogServiceDirtyRead implements BlogServiceTxThread {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlogServiceDirtyRead.class);
    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(CountDownLatch latch) {
        new Thread(() -> {
            DefaultTransactionDefinition definition =
                    new DefaultTransactionDefinition(
                            TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
            TransactionStatus status = txManager.getTransaction(definition);
            String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
            try {
                Thread.sleep(1000);
                int update = jdbcTemplate.update(sql, "今日要闻", "巴以冲突");
                if (update > 0) {
                    LOGGER.info("{}线程插入数据成功",
                            Thread.currentThread().getName());
                } else {
                    LOGGER.info("{}线程插入数据失败",
                            Thread.currentThread().getName());
                }
                Thread.sleep(5000);//提交事务前休眠五秒，让t2线程此时去查询数据
                txManager.commit(status);
                LOGGER.info("{}线程事务提交成功", Thread.currentThread().getName());
            } catch (Exception e) {
                txManager.rollback(status);
                LOGGER.info("{}线程事务回滚", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }finally {
                latch.countDown();
            }
        }, "t1").start();


        new Thread(() -> {
            String sql = "SELECT * FROM posts";
            List<Post> query1 = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Post.class));
            LOGGER.info("{}线程查询到了{}条数据", Thread.currentThread().getName(),
                    query1.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //t1线程还没有提交事务，此时如果读取到数据那么就表明发生了脏读
            List<Post> query2 = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Post.class));
            LOGGER.info("{}线程查询到了{}条数据",
                    Thread.currentThread().getName(),
                    query2.size());
            latch.countDown();
        }, "t2").start();
    }
}
