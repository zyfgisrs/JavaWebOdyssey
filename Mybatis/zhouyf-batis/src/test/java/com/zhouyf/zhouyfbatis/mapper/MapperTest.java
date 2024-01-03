package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.ArticlePO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testInsert(){
        ArticlePO article = new ArticlePO();
        article.setUserId(19000);
        article.setTitle("MySQL必知必会");
        article.setReadCount(100);
        article.setCreateTime(LocalDateTime.of(2022,1,2,11,11,23));
        article.setUpdateTime(LocalDateTime.of(2022, 5, 2, 11, 11, 23));
        article.setSummary("学习MySQL的最佳书籍");
        int i = articleMapper.insertArticle(article);
        if(i > 0) {
            log.info("成功添加了{}本书", i);
        }
    }

    @Test
    void testUpdate(){
        int i = articleMapper.updateReadCount(1, 900);
        if(i > 0) {
            log.info("成功修改了{}本书的信息", i);
        }
    }

    @Test
    void testDelete(){
        int i = articleMapper.deleteArticle(1);
        if(i > 0) {
            log.info("成功删除了{}本书", i);
        }
    }

    @Test
    void testSelect(){
        ArticlePO articlePO = articleMapper.selectById(2);
        System.out.println(articlePO);
    }
}
