# Spring声明式事务

在Spring框架中，声明式事务管理是通过在代码中使用注解或者在配置文件中进行配置来实现事务管理的。这种方式将事务管理从业务逻辑代码中解耦出来，使得代码更为简洁，易于维护。

## 基本概念

- **事务（Transaction）**：事务是一组逻辑上的操作，它们作为一个整体被执行，保证所有操作都成功完成，或者在发生错误时都不会执行。
- **声明式事务（Declarative Transaction Management）**：是一种允许你通过配置而不是编程来管理事务的方法。它允许你在不触及代码的情况下，控制事务的边界和属性。

## 声明式事务的配置

使用注解`@Transactional`注解：通过在类或者方法上添加`@Transactional`注解来声明事务边界和属性。

- 配置类`com.zhouyf.config.TransactionConfig`，`@EnableTransactionManagement`注解启用事务管理

若使用配置文件来进行配置则为：

```
<tx:annotation-driven transaction-manager="transactionManager"/>。
```

```java
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
```

- `com.zhouyf.service.impl.UserServiceImpl`负责处理用户的创建

```java
package com.zhouyf.service.impl;

import com.zhouyf.pojo.User;
import com.zhouyf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (id, username, password) VALUES (?, ?, ?)";
        int effectedRows = jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword());
        if(effectedRows > 0){
            LOGGER.info("用户创建成功");
        }
    }
}
```

- `com.zhouyf.service.impl.UserSettingsServiceImpl`用户设置操作

```java
package com.zhouyf.service.impl;

import com.zhouyf.pojo.UserSettings;
import com.zhouyf.service.UserSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void save(UserSettings userSettings) {
        String sql = "INSERT INTO usersettings (userId, theme) VALUES (?, ?)";
        int effectedRows = jdbcTemplate.update(sql,
                userSettings.getUserId(), userSettings.getTheme(), 1);
        if(effectedRows > 0){
            LOGGER.info("用户设置保存成功");
        }
    }
}
```

- `@Transactional`注解被应用于`createUserWithDefaultSettings`方法，`createUserWithDefaultSettings`方法先执行`UserServiceImpl.save(user)`方法再执行`UserSettingsServiceImpl.save(settings)`方法。

在事务上下文中，`UserServiceImpl.save(user)`和`UserSettingsServiceImpl.save(settings)`两个操作都成功完成，事务将被提交，否则事务将被回滚，并且在数据库中不会保存任何数据。通过这种方式，我们能确保数据的完整性和一致性。

```java
package com.zhouyf.service.impl;


import com.zhouyf.pojo.User;
import com.zhouyf.pojo.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCreateService {
    @Autowired
    private UserSettingsServiceImpl userSettingsService;

    @Autowired
    private UserServiceImpl userService;


    @Transactional
    public void createUserWithDefaultSettings(User user){
        userService.save(user);

        UserSettings userSettings = new UserSettings();
        userSettings.setUserId(user.getId());
        userSettings.setTheme("default");
        userSettingsService.save(userSettings);
    }
}
```

测试类

```java
package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import com.zhouyf.pojo.User;
import com.zhouyf.service.impl.UserCreateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HikariDataSourceConfig.class, TransactionConfig.class})
public class UserCreateServiceTest {
    @Autowired
    private UserCreateService userCreateService;


    @Test
    void test(){
        User user = new User();
        user.setId(1000003L);
        user.setUsername("zhouyf");
        user.setPassword("2563085");
        userCreateService.createUserWithDefaultSettings(user);
    }
}
```

当在`UserSettingsServiceImpl.save(settings)`设置错误时，事务将会回滚

![image-20231105182737343](assets/image-20231105182737343.png)

## 事务的只读标志

> Spring框架的事务管理中，提供了一个只读（Read-Only）标志，可以通过 `@Transactional` 注解或者在XML配置中设置。当你标记一个事务为只读时，你实际上是在告诉事务管理器这个事务只会读取数据，不会修改数据。

- 在Java代码中，可以通过 `@Transactional` 注解设置只读标志：

```java
@Service
public class UserService {

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        // ...查询逻辑
    }
}
```

**只读标志的作用**:

- 性能优化：
  - 当事务管理器知道一个事务是只读的时，它可能会做一些优化，比如避免执行不必要的flush操作（同步数据库操作），或者选择更优的事务隔离级别，从而提升应用的性能。
- 数据保护：
  - 只读标志也是一种代码约定，它清晰地表明了事务的意图，即这个事务不应该修改任何数据。这有助于保护数据不被错误的修改，尤其是在大型项目或团队协作时。

**只读标志的注意事项**：

- 不是强制性的：
  - 只读标志不会强制事务只能读取数据，如果在一个标记为只读的事务中执行了写操作，这个写操作仍然会被执行。但是，这可能会违反你的意图，也可能会产生不可预期的后果。
- 配合事务隔离级别：
  - 在设置只读标志时，还应考虑事务的隔离级别。不同的隔离级别会影响事务的行为和系统的性能。

## 事务的回滚规则

Spring的事务管理提供了一套完善的回滚策略，以确保在遇到异常或错误时，可以将数据库恢复到一个一致的状态。下面是关于Spring事务回滚规则的层次性解释：

 **默认的回滚规则**:

- 默认情况下，Spring只在运行时异常(Runtime exceptions)和错误(Errors)发生时才回滚事务，而在检查异常(Checked exceptions)发生时不回滚事务。运行时异常和错误通常是程序中不可恢复的严重问题，所以Spring选择在这些情况下回滚事务以保护数据的完整性。

```java
@Service
public class UserServiceImpl implements UserService {
    @Transactional
    public void registerUser(User user) {
        // ...操作数据库
        throw new RuntimeException("Unexpected error");  // 发生运行时异常，事务回滚
    }
}
```

 **自定义回滚规则**:

- 你可以通过 `@Transactional` 注解来自定义回滚规则，指定在什么样的异常发生时回滚事务。

```java
@Service
public class UserServiceImpl implements UserService {
    @Transactional(rollbackFor = Exception.class)  // 所有异常都会导致事务回滚
    public void registerUser(User user) {
        // ...操作数据库
        throw new Exception("Checked exception");  // 发生检查异常，事务回滚
    }
}
```

**指定不回滚的异常**:

- 除了指定何时回滚事务，你还可以指定在某些异常发生时不回滚事务。

```java
@Service
public class UserServiceImpl implements UserService {
    @Transactional(noRollbackFor = UserExistsException.class)  // 在UserExistsException异常发生时不回滚事务
    public void registerUser(User user) {
        // ...操作数据库
        throw new UserExistsException("User already exists");  // 发生UserExistsException异常，事务不回滚
    }
}
```

