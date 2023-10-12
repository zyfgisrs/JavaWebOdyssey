# Spring环境搭建

## 项目创建

- 创建项目myspring

<img src="assets/image-20231011145544008.png" alt="image-20231011145544008" style="zoom:50%;" />

- 配置Spring核心依赖

```
implementation group: 'org.springframework', name: 'spring-context', version: '6.0.7'
```

![image-20231011145852982](assets/image-20231011145852982.png)

## JUnit5测试框架

- 配置JUnit5测试框架

```groovy
    testImplementation group: 'org.springframework', name: 'spring-test', version: '6.0.7'
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-api', version: '5.9.2'
    testImplementation group: 'org.junit.vintage', name: 'junit-vintage-engine', version: '5.9.2'
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-engine', version: '5.9.2'
    testImplementation group: 'org.junit.platform', name: 'junit-platform-launcher', version: '1.9.2'
```

- 刷新项目

![image-20231011150339046](assets/image-20231011150339046.png)

- 编写com.zhouyf.pojo.Book类

```groovy
package com.zhouyf.pojo;

public class Book {
    private String name;

    private double price;
}
```

- 编写`com.zhouyf.service.IBookService`接口和实现类`com.zhouyf.service.impl.BookServiceImpl`

```java
package com.zhouyf.service;

import com.zhouyf.pojo.Book;

public interface IBookService {
    void insertBook(Book book);
}
```

```java
package com.zhouyf.service.impl;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.IBookService;

public class BookServiceImpl implements IBookService {
    @Override
    public void insertBook(Book book) {
        System.out.println("插入了一本书:" + book.getName() + " " + book.getPrice());
    }
}
```

- 将BookServiceImpl准入Spring容器，编写配置文件

![image-20231011151928601](assets/image-20231011151928601.png)

- 创建配置文件`spring-service`

```xml
<bean id="bookServiceImpl" class="com.zhouyf.service.impl.BookServiceImpl"/>
```

- 测试类`myspring\src\test\java\com\zhouyf\test\BookServiceTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BookServiceTest {
    @Autowired
    private BookServiceImpl bookService;


    @Test
    void test(){
        Book book = new Book("三国演义", 12.2);
        bookService.insertBook(book);
    }
}
```

- 修改测试的环境

![image-20231011153152668](assets/image-20231011153152668.png)

- 运行测试

![image-20231011153404301](assets/image-20231011153404301.png)

## Logback日志组件

- 添加依赖

```groovy
testImplementation group: 'ch.qos.logback', name: 'logback-classic', version: '1.4.6'
implementation group: 'org.slf4j', name: 'slf4j-api', version: '2.0.7'
```

- 测试类中测试日志

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BookServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceTest.class);
    @Autowired
    private BookServiceImpl bookService;


    @Test
    void test(){
        Book book = new Book("三国演义", 12.2);
        LOGGER.info("【LOGGER】插入一本书");
        bookService.insertBook(book);
    }
}
```

Output：

```
15:55:55.146 [main] INFO com.zhouyf.test.BookServiceTest -- 【LOGGER】插入一本树
插入了一本书:三国演义 12.2
```

