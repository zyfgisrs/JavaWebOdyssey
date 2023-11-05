package com.zhouyf.service.impl;

import com.zhouyf.pojo.Post;
import com.zhouyf.service.BlogServiceTxThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class BlogServicePhantomRead implements BlogServiceTxThread {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlogServicePhantomRead.class);
    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(CountDownLatch latch) {
        new Thread(() -> {
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = txManager.getTransaction(definition);
            LOGGER.info("{}线程开启事务", Thread.currentThread().getName());
            String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
            try {
                Thread.sleep(1000);
                int update = jdbcTemplate.update(sql, "今日要闻", "JDG战胜KT");
                if (update > 0) {
                    LOGGER.info("{}线程插入数据成功", Thread.currentThread().getName());
                } else {
                    LOGGER.info("{}线程插入数据失败", Thread.currentThread().getName());
                }
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
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.ISOLATION_READ_COMMITTED);
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = txManager.getTransaction(definition);
            LOGGER.info("{}线程开启事务", Thread.currentThread().getName());
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
            String sql = "SELECT * FROM posts WHERE title = :title";
            HashMap<String, Object> params = new HashMap<>();
            params.put("title", "今日要闻");
            List<Post> query1 = template.query(sql, params, new BeanPropertyRowMapper<>(Post.class));
            LOGGER.info("{}线程查询到了{}条数据", Thread.currentThread().getName(), query1.size());
            try {
                Thread.sleep(3000);
                //模拟其他业务操作
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //再次查询相同范围的数据
            List<Post> query2 = template.query(sql, params, new BeanPropertyRowMapper<>(Post.class));
            LOGGER.info("{}线程查询到了{}条数据", Thread.currentThread().getName(), query2.size());
            if(query1.size() != query2.size()){
                LOGGER.warn("{}线程出现了幻读，事务回滚", Thread.currentThread().getName());
                txManager.rollback(status);
            }else {
                txManager.commit(status);
                LOGGER.info("{}线程执行正常，事务提交", Thread.currentThread().getName());
            }
            latch.countDown();
        }, "t2").start();
    }
}
