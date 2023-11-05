# 整合JDBC

在Spring工程中，JDBC（Java Database Connectivity）是用来直接与数据库进行交互的一套Java API。Spring框架通过Spring JDBC模块提供了一个更高层次的抽象，简化了原生`JDBC API`的复杂性。以下是Spring中JDBC的作用：

1. **数据访问抽象**：Spring JDBC提供了一个抽象层来避免冗长的JDBC代码和异常处理，让开发人员可以更加专注于业务逻辑而非数据库的细节。
2. **异常处理**：Spring提供了一个异常层次结构，将SQL异常转换为`DataAccessException`，这是一个运行时异常（unchecked exception）。这使得异常处理更加一致，并能够将数据库错误透明地转换为Spring的数据访问异常。
3. **资源管理**：Spring管理数据库连接和其他资源，确保资源被正确开启、使用和关闭，这减少了潜在的资源泄露。
4. **模板设计模式**：Spring的`JdbcTemplate`类采用了模板方法设计模式，它提供了模板方法来执行数据库操作，比如查询、更新、插入和删除，而开发人员只需要提供SQL语句和定义如何转换结果。
5. **声明式事务管理**：Spring支持声明式事务管理，这允许开发人员通过配置来管理事务边界，而不是硬编码事务逻辑。
6. **简化的数据库访问和操作**：提供了各种简便方法来简化CRUD（创建、读取、更新、删除）操作，并且支持批量更新和批量插入。
7. **支持多种数据源**：Spring可以很容易地配置为使用多种数据源，并为测试和实际部署提供了灵活性。
8. **集成其他Spring模块**：Spring JDBC可以与Spring的其他模块（如Spring ORM）无缝集成，提供对JPA、Hibernate等技术的支持。
9. **数据访问对象（DAO）支持**：Spring提供了一个DAO层的编程模型，使得实现和使用DAO类更加容易。

## 依赖引入

```groovy
dependencies {
    //Mysql数据库驱动的依赖
    implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.28'
    //Spring JDBC
    implementation group: 'org.springframework', name: 'spring-jdbc', version: '6.0.7'
}
```

## JDBC配置

`DriverManagerDataSource` 是 Spring 框架中用于简化JDBC配置的一个类。这个类属于 `org.springframework.jdbc.datasource` 包，实现了 Spring 的 `DataSource` 接口，提供了一种快速配置数据源的方法。

主要特点和用途如下：

1. **简单配置**：只需要提供JDBC驱动、数据库URL、用户名和密码即可配置。
2. **非池化连接**：它不支持JDBC连接池，意味着不适合用在生产环境中。每次对数据库的调用都会创建一个新的数据库连接，并且在使用完后需要手动关闭。
3. **便于测试**：通常在开发或测试环境中使用，当不需要高级功能如连接池时，它是一个很好的选择。
4. **实现DataSource接口**：可以与Spring的`JdbcTemplate`以及其他数据访问辅助类一起使用。

`com.zhouyf.config.DataSourceConfig`

```java
package com.zhouyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
```

测试配置`com.zhouyf.test.DataSourceTest`

```java
package com.zhouyf.test;

import com.zhouyf.config.DataSourceConfig;
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
@ContextConfiguration(classes = DataSourceConfig.class)
public class DataSourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceTest.class);
    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        LOGGER.info("数据库连接对象:{}", this.dataSource.getConnection());
    }
}
```

打印日志：

```
2023-11-03 14:30:33.499 [main] INFO  com.zhouyf.test.DataSourceTest - 数据库连接对象:com.mysql.cj.jdbc.ConnectionImpl@982bb90
```

## JdbcTemplate编程

- 创建表`posts`

```sql
CREATE TABLE IF NOT EXISTS posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    published_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- 创建pojo类`com.zhouyf.pojo.post`

```java
package com.zhouyf.pojo;

import java.util.Date;

public class Post {
    private int id;
    private String title;

    private String content;

    private Date published_at;

    public Post(int id, String title, String content, Date published_at){
        this.id = id;
        this.title = title;
        this.content = content;
        this.published_at = published_at;
    }

    public Post(){
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", published_at=" + published_at +
                '}';
    }
}
```

- Service接口和实现类，增删改查

```java
package com.zhouyf.service;

import com.zhouyf.pojo.Post;

import java.util.List;

public interface BlogService {
    void addPost(String title, String content);

    void removePost(int id);

    void updatePost(int id, String title);

    List<Post> showAllPosts();
}
```

```java
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
```

- 配置类`com.zhouyf.config.DataSourceConfig`

```java
package com.zhouyf.config;

import com.zhouyf.service.impl.BlogServiceImpl;
import com.zhouyf.service.impl.BookServiceImpl;
import com.zhouyf.service.impl.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = BlogServiceImpl.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = {BookServiceImpl.class, MessageServiceImpl.class}))
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
```

- 测试增删改查

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class PortServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortServiceTest.class);
    @Autowired
    private BlogService blogService;
    @Test
    void test(){
        blogService.addPost("减轻偷猎问题","赞比亚的某个村庄采取养蜂业来降低偷猎的影响");
        blogService.addPost("巴以冲突","联合国安理会关注加沙地带的冲突和暴力事件");
        blogService.addPost("创意能改变世界吗？","全球政府担忧外国投资者可以通过ISDS规则，针对减少其利润的新法律或政策起诉它们");
        blogService.addPost("COVID-19疫苗犹豫", "分享‘实时’数据，保持信息简明一致有助于解决疫苗犹豫问题");
        blogService.addPost("女性与战争", "探讨战争对女性的影响，以及女性如何在战争和冲突中发挥作用");
        blogService.addPost("战争罪行", "拜登被视为否认和支持以色列正在进行的战争罪行的首脑");
        blogService.removePost(2);
        blogService.updatePost(6, "战争的罪行");
        List<Post> posts = blogService.showAllPosts();
        for (Post post : posts) {
            System.out.println(post);
        }
    }
}
```

输出日志：

![image-20231103165951881](assets/image-20231103165951881.png)

## 补充

- `jdbcTemplate` bean被定义为一个单例，并且通过Spring的IoC容器注入到需要它的其他bean中

- `JdbcTemplate`是线程安全的，它不保留任何与特定线程相关的状态。`JdbcTemplate`的线程安全性主要是通过对底层`DataSource`的正确使用来实现的。每个线程从`DataSource`获取自己的数据库连接，执行数据库操作，然后关闭连接。这确保了每个线程都在其自己独立的数据库连接上执行操作，从而实现线程安全。

