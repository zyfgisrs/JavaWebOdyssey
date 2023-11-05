# 整合`HikariCP`数据库连接池

## `HikariCP`数据库连接池

使用`DriverMangerDataSource`获取了数据库的连接，但这种方式连接数据库的性能较差。这种连接的方式是在每一次获取连接的时候才进行数据库的连接操作。在数据库连接的处理之中，一定会建立若干个Socket连接，那么会有耗时，而在数据库关闭的时候也会存在有同样的耗时处理，所以在实际的项目之中最佳的数据库连接方式一定是通过连接池实现的。可以考虑在Spring内部去实现一个连接池的维护，Spring默认推荐的数据库连接池组件就是HikariCP，不建议再使用其他的数据库连接池组件。

`HikariCP` 是一个性能非常好的数据库连接池，广泛被认为是当前Java平台中连接池技术的领导者。HikariCP 以其简单性、性能、并且消耗资源少著称。它比其他连接池（如Apache DBCP和c3p0）提供了更快的连接获取速度，并且更少的“响应时间抖动”。

## 依赖配置

```groovy
dependencies {
    //HikariCP数据源
    implementation group: 'com.zaxxer', name: 'HikariCP', version: '5.0.1'
}
```

## `HikariCP`数据库参数配置

- `resources/datasource`

```properties
zyf.database.driverClassName=com.mysql.cj.jdbc.Driver
zyf.database.jdbcUrl=jdbc:mysql://localhost:3306/blog
zyf.database.username=root
zyf.database.password=123456
#【HikariCP】配置当前数据是否只读
zyf.database.readOnly=false
#【HikariCP】配置数据库连接超时时间(毫秒)
zyf.database.connectionTimeOut=3000
#【HikariCP】配置一个连接最小的维持时间长度
zyf.database.pool.idleTimeOut=3000
#【HikariCP】配置一个连接最长的存活时间
zyf.database.pool.maxLifetime=6000
#【HikariCP】配置一个连接池最大连接对象数量
zyf.database.pool.maximumPoolSize=60
#【HikariCP】数据库没有任何用户访问时，最少维持20个数据库连接
zyf.database.pool.minimumIdle=20
```

## 配置数据源

- `com.zhouyf.config.HikariDataSourceConfig`

```java
package com.zhouyf.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zhouyf.service.impl.BlogServiceImpl;
import com.zhouyf.service.impl.BookServiceImpl;
import com.zhouyf.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:/datasource.properties")
@ComponentScan(basePackageClasses = BlogServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {BookServiceImpl.class, MessageServiceImpl.class}))
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
```

## 测试数据源

```java
import com.zhouyf.config.HikariDataSourceConfig;
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
@ContextConfiguration(classes = HikariDataSourceConfig.class)
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

日志输出：

```
com.zhouyf.test.DataSourceTest - 数据库连接对象:HikariProxyConnection@2066945445 wrapping com.mysql.cj.jdbc.ConnectionImpl@7cab1508
```

## 使用HikariCP数据源进行增删改查

使用`DriverMangerDataSource`获取了数据库的连接，在只需要将数据源进行替换即可。

将上下文环境替换为`HikariDataSourceConfig`配置，即可使用HikariCP数据源

```java
package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.pojo.Post;
import com.zhouyf.service.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HikariDataSourceConfig.class)
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

打印日志信息，可以看到通过HikariCP数据源进行了连接，并成功执行了增删改查：

![image-20231103195137342](assets/image-20231103195137342.png)

