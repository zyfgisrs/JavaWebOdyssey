package com.zhouyf.service.impl;

import com.zhouyf.pojo.Post;
import com.zhouyf.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addPost(String title, String content) {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, title, content);
        if (rowsAffected > 0) {
            LOGGER.info("Insert successful, {} row(s) affected", rowsAffected);
        } else {
            LOGGER.warn("Insert failed, no rows affected.");
        }
    }

    @Override
    public void removePost(int id) {
        String sql = "DELETE FROM posts WHERE id=?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected > 0) {
            LOGGER.info("DELETE successful, {} row(s) affected", rowsAffected);
        } else {
            LOGGER.warn("DELETE failed, no rows affected.");
        }
    }

    @Override
    public void updatePost(int id, String title) {
        String sql = "UPDATE posts SET title=? WHERE id=?";
        int rowsAffected = jdbcTemplate.update(sql, title, id);
        if (rowsAffected > 0) {
            LOGGER.info("UPDATE successful, {} row(s) affected", rowsAffected);
        } else {
            LOGGER.warn("UPDATE failed, no rows affected.");
        }
    }

    @Override
    public List<Post> showAllPosts() {
        String sql = "SELECT * FROM posts";
        List<Post> posts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
        LOGGER.info("查询到{}条数据", posts.size());
        return posts;
    }
}
