# Spring编程式事务管理

编程式事务管理是一种在代码中显式管理事务边界的方法。在Spring框架中，编程式事务管理通常通过使用`TransactionTemplate`类或`PlatformTransactionManager`接口的实现来完成。

编程式事务管理允许在代码中明确控制事务的开始、提交和回滚。它提供了对事务管理的精细控制，但可能会使代码变得更加复杂。

## 准备事务管理器

在开始编程式事务管理之前，需要配置一个事务管理器。例如，对于JDBC，使用`DataSourceTransactionManager`。

- `com.zhouyf.config.TransactionConfig`

```java
package com.zhouyf.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager transactionManager(HikariDataSource source) {
        return new DataSourceTransactionManager(source);
    }
}
```

## 事务入门

- `com.zhouyf.service.impl.BlogServiceTxImpl`

在业务处理代码中将会执行4条插入的操作，其中第四条将会报错

```java
package com.zhouyf.service.impl;

import com.zhouyf.service.BlogServiceTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class BlogServiceTxImpl implements BlogServiceTx {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add() {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(definition);//开启事务
        try {
            jdbcTemplate.update(sql, "111", "111");
            jdbcTemplate.update(sql, "222", "111");
            jdbcTemplate.update(sql, "333", "111");
            jdbcTemplate.update(sql, "111", "111", 1);//设置错误
            txManager.commit(status);//提交
            LOGGER.info("全部执行成功，事务提交");
        } catch (DataAccessException e) {
            txManager.rollback(status);
            LOGGER.info("执行失败，事务回滚");//回滚
            throw new RuntimeException(e);
        }
    }
}
```

测试：

```java
package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import com.zhouyf.service.BlogServiceTx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionConfig.class, HikariDataSourceConfig.class})
public class TxMangeTest {

    @Autowired
    private BlogServiceTx blogServiceTx;

    @Test
    void test(){
        blogServiceTx.add();
    }
}
```

输出

```
[main] INFO  com.zhouyf.service.impl.BlogServiceImpl - 执行失败，事务回滚
```

## 基础组件

### 三大基础组件

在Spring框架中，`PlatformTransactionManager`, `TransactionDefinition`, 和 `TransactionStatus` 这三个接口或类是编程式事务管理的基础组件。它们共同协作，提供了一套完整且灵活的事务管理机制。下面我会详细解释它们各自的角色以及它们之间的关系，来帮助你理解Spring中编程式事务管理的工作原理。

1. **PlatformTransactionManager**:

   - `PlatformTransactionManager` 是一个接口，它是Spring事务管理的核心，定义了事务管理的基本操作，如创建、提交和回滚事务。
   - 在实际应用中，根据不同的数据访问框架（如JDBC, Hibernate, JPA等），Spring提供了多种`PlatformTransactionManager`的实现。

   ```
   TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
   ```

   ```
   void commit(TransactionStatus status)
   ```

   ```
   void rollback(TransactionStatus status)
   ```

2. **TransactionDefinition**:

   - `TransactionDefinition` 是一个接口，它定义了事务的各种属性，如隔离级别、传播行为、超时时间和只读标记等。
   - 在Spring中，通常使用 `TransactionDefinition` 的实现类 `DefaultTransactionDefinition` 来设置事务的属性。

3. **TransactionStatus**:

   - `TransactionStatus` 是一个接口，它包含了事务的运行时状态，如是否是新的事务、是否已经完成，以及是否被标记为回滚等。
   - 你可以通过 `TransactionStatus` 来检查事务的状态，以及在需要的时候标记事务为回滚状态。

### 协作关系

1. 当开始一个新事务时，首先你会创建一个 `TransactionDefinition` 对象（或其实现类 `DefaultTransactionDefinition` 对象）来指定事务的属性。
2. 然后通过 `PlatformTransactionManager` 的 `getTransaction()` 方法，并传递 `TransactionDefinition` 对象作为参数，来获取一个 `TransactionStatus` 对象，该对象表示新创建的事务的状态。（如果使用的是JDBC的事务管理，那么就是使用`DataSourceTransactionManager`实现类。）
3. 在事务的执行过程中，你可以通过 `TransactionStatus` 对象来检查事务的状态，以及在需要的时候标记事务为回滚状态。
4. 最终，通过 `PlatformTransactionManager` 的 `commit()` 或 `rollback()` 方法来提交或回滚事务，并传递 `TransactionStatus` 对象来指定要提交或回滚的事务。

## `savepoint`

通过TransactionStatus对象可以获取保存点，在必要的时候可以让事务回到保存点的状态。本例演示保存点的使用：

- `com.zhouyf.service.impl.BlogServiceTx`

```java
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
```

打印日志：

```
com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@34a0ef00
com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
com.zhouyf.service.impl.BlogServiceImpl - 设置保存点com.mysql.cj.jdbc.MysqlSavepoint@59496961
com.zhouyf.service.impl.BlogServiceImpl - 发生错误，归滚到保存点com.mysql.cj.jdbc.MysqlSavepoint@59496961
com.zhouyf.service.impl.BlogServiceImpl - 事务提交
```

数据库中保存了保存点之前的两条数据：

![image-20231104200348707](assets/image-20231104200348707.png)

# 脏读、不可重复读、幻读

脏读、不可重复读和幻读是数据库事务处理中的常见问题，它们都是由于并发事务的执行而导致的数据不一致问题。了解这些问题对于选择正确的事务隔离级别和编写可靠的数据库交互代码非常重要。现在，让我们一一解析这三个概念：

1. **脏读 (Dirty Read)**:
    - 脏读是指在一个事务中读取到了另一个未提交事务的数据。如果这个未提交的事务最终被回滚，那么第一个事务读取到的数据就是无效的。
    - 举例：事务A修改了一条数据，但还没有提交，事务B读取了这条数据。如果事务A最终回滚，那么事务B读取到的数据就是错误的。

2. **不可重复读 (Non-repeatable Read)**:
    - 不可重复读是指在同一个事务中，对同一数据的多次读取结果不一致。这通常是因为在两次读取之间，有另一个事务修改或删除了这些数据。
    - 举例：事务A读取了一条数据，事务B在此期间修改了这条数据并提交，然后事务A再次读取这条数据，发现数据已经发生了变化。

3. **幻读 (Phantom Read)**:
    - 幻读是指在同一个事务中，对同一查询的两次结果不一致。这通常是因为在两次查询之间，有另一个事务插入或删除了一些数据。
    - 举例：事务A查询了满足某些条件的所有数据，事务B在此期间插入了一些新的满足条件的数据并提交，然后事务A再次查询，发现多了一些新的数据。

不同的事务隔离级别能够解决不同的读问题，例如：

- **读已提交 (Read Committed)**: 解决脏读问题，但可能出现不可重复读和幻读。
- **可重复读 (Repeatable Read)**: 解决脏读和不可重复读问题，但可能出现幻读。
- **串行化 (Serializable)**: 解决脏读、不可重复读和幻读问题，但并发性能最低。

# 事务隔离级别

在Spring框架中，事务隔离级别是通过`TransactionDefinition`接口的`setIsolationLevel`方法来设置的。事务隔离级别决定了一个事务可能受其他并发事务影响的程度。以下是对Spring中事务隔离级别的详细解释，包括每个隔离级别的特点和应用场景。

1. **未提交读（Read Uncommitted）**:
   - 在未提交读隔离级别下，一个事务可以读取到其他事务未提交的数据。
   - 这是最低的隔离级别，性能最好，但并发问题最多，如脏读、不可重复读和幻读。
   - 在Spring中，该隔离级别通过常量`TransactionDefinition.ISOLATION_READ_UNCOMMITTED`来表示。

```java
definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
```

2. **已提交读（Read Committed）**:
   - 在已提交读隔离级别下，一个事务只能读取到其他事务已提交的数据。
   - 这个级别可以避免脏读，但可能会发生不可重复读和幻读。
   - 在Spring中，该隔离级别通过常量`TransactionDefinition.ISOLATION_READ_COMMITTED`来表示。

```java
definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
```

3. **可重复读（Repeatable Read）**:
   - 在可重复读隔离级别下，事务在开始时会创建一个数据的快照，只能读取快照中的数据，而不是实时数据，从而避免了不可重复读问题。
   - 这个级别可以避免脏读和不可重复读，但可能会发生幻读。
   - 在Spring中，该隔离级别通过常量`TransactionDefinition.ISOLATION_REPEATABLE_READ`来表示。

```java
definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
```

4. **串行化（Serializable）**:
   - 在串行化隔离级别下，事务是顺序执行的，不会发生并发问题，但性能最差。
   - 这是最高的隔离级别，可以避免脏读、不可重复读和幻读。
   - 在Spring中，该隔离级别通过常量`TransactionDefinition.ISOLATION_SERIALIZABLE`来表示。

```java
definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
```

## 模拟脏读

将t1线程的事务隔离级别设置为读未提交，在t1线程事务提交之前，t2线程查询到t1线程未提交的操作数据，因此用来验证发生了脏读。

```java
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
```

打印日志如下：

```
t2线程查询到了0条数据
t1线程插入数据成功
t2线程查询到了1条数据
t1线程事务提交成功
```

## 模拟幻读

```java
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
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
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
```

```
t2线程开启事务
t1线程开启事务
t2线程查询到了10条数据
t1线程插入数据成功
t1线程事务提交成功
t2线程查询到了11条数据
t2线程出现了幻读，事务回滚
```

将t2线程中的隔离级别更换为可重复读，幻读没有出现

```
 t1线程开启事务
 t2线程开启事务
 t2线程查询到了11条数据
 t1线程插入数据成功
 t1线程事务提交成功
 t2线程查询到了11条数据
 t2线程执行正常，事务提交
```

> MySQL 在「可重复读」隔离级别下，可以很大程度上避免幻读现象的发生（注意是很大程度避免，并不是彻底避免），所以 MySQL 并不会使用「串行化」隔离级别来避免幻读现象的发生，因为使用「串行化」隔离级别会影响性能。

# 事务传播行为

> 事务传播行为决定了在一个事务性方法被另一个事务性方法调用时，事务如何传播。

1. **`PROPAGATION_REQUIRED`**:
   - 当一个事务方法被另一个事务方法调用时，它将运行在现有的事务中。如果没有现有的事务，它将启动一个新的事务。
   - 这是最常见的选择，并且也是Spring事务管理的默认传播行为。
   - 例如，如果方法A调用方法B，且两者都标记为`PROPAGATION_REQUIRED`，则方法B将加入方法A的事务。如果事务在方法B中失败，则整个事务（包括方法A和方法B的操作）都将回滚。
2. **`PROPAGATION_SUPPORTS`**:
   - 当一个事务方法被另一个事务方法调用时，它是可选的运行在现有的事务中。如果没有现有的事务，它将在非事务性的执行上下文中运行。
   - 这种传播行为允许方法在存在事务的情况下参与事务，但在没有事务的情况下仍然能够执行。
   - 例如，如果方法A调用方法B，且方法B标记为`PROPAGATION_SUPPORTS`，但方法A没有事务，那么方法B将在非事务性的执行上下文中运行。
3. **`PROPAGATION_MANDATORY`**:
   - 这种传播行为要求必须在一个现有的事务上下文中执行，否则它将抛出一个异常。如果没有活动的事务，将抛出`IllegalTransactionStateException`。
4. **`PROPAGATION_REQUIRES_NEW`**:
   - 这种传播行为总是会启动一个新的事务。如果有一个现有的事务，它会被暂停。
5. **`PROPAGATION_NOT_SUPPORTED`**:
   - 这种传播行为执行非事务性执行，如果有一个现有的事务，它会被暂停。
6. **`PROPAGATION_NEVER`**:
   - 这种传播行为表示，如果存在一个活动的事务，它将抛出一个异常。它总是执行非事务性执行。
7. **`PROPAGATION_NESTED`**:
   - 这种传播行为表示，如果存在一个活动的事务，它应该嵌套事务作为一个嵌套事务来执行。如果没有活动的事务，它的行为类似于`PROPAGATION_REQUIRED`。
   - 嵌套事务是一个可以独立于封闭事务提交或回滚的子事务。如果封闭事务失败，它将回滚嵌套事务所做的所有操作。





