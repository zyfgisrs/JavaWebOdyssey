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

### 相关概念

**SLF4J：**

- SLF4J是一个日志门面（Facade），意味着它提供了一个简单统一的日志记录接口，使得应用程序可以通过这个接口来进行日志记录。
- 它本身不提供日志的实现，而是允许开发者在底层选择不同的日志实现框架。
- 使用SLF4J的好处在于，应用代码可以独立于具体的日志实现，这意味着开发者可以在不改变应用程序代码的情况下，切换底层的日志实现框架。

**Logback：**

- Logback是SLF4J的一个日志实现，它是log4j的一个改进版，由相同的作者设计。
- 它被设计来更快、更灵活，并且拥有更多的配置功能。
- Logback自然集成了SLF4J的API，因此如果你的应用程序使用SLF4J作为日志接口，而且你选择Logback作为实现，通常你不需要做额外的集成工作。

总结来说，SLF4J和Logback的关系是“接口”和“实现”的关系。你可以把SLF4J理解为一个抽象层，它允许你通过相同的API来使用Logback或其他日志系统（如log4j，JUL等）。而Logback就是这个抽象层下面的一个具体实现。在实际的项目中，经常会看到这样的组合，即使用SLF4J作为应用程序中的日志API，而Logback作为日志的实现，这样做可以在将来需要替换日志实现时，减少对代码的影响。

### 环境配置

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

### 日志级别

志的输出级别是指定日志信息重要性的一个指标，它帮助开发者对输出的日志信息进行分类和过滤。不同的日志级别代表了不同的意义，通常用于控制日志信息的详细程度以及在生产环境中输出什么样的信息。

以下是常见的日志级别，按照从最低到最高的顺序排列：

1. **TRACE** - 这是最详细的日志级别，通常用于开发环境，用以追踪问题时输出详尽的信息，比如程序的流程跟踪、变量值等。
2. **DEBUG** - 次详细级别的日志，用于输出对开发人员调试应用程序有帮助的信息。这个级别的日志在生产环境中通常是关闭的，因为可能会产生大量的日志输出。
3. **INFO** - 提供一般信息，比如程序启动或运行状态。这个级别的信息对于了解系统运行过程中的一些重要节点很有帮助。
4. **WARN** - 警告级别的日志表示可能的问题，它并不会影响系统的运行，但可能会在将来某个时间点导致问题。
5. **ERROR** - 错误级别的日志指出了不影响整个应用程序运行，但是需要被注意到的问题。通常这些问题需要开发者去查找原因并修复。
6. **FATAL** - 最高级别的日志，表明了非常严重的错误，这种级别的错误通常会导致应用程序终止运行。

日志级别的设定是一个关键的操作，它可以帮助开发者和系统管理员在必要的时候获得足够的信息，同时又避免了信息过载。例如，在开发过程中可能需要DEBUG或TRACE级别的日志来查找问题，但在生产环境中，一般会设置为INFO或WARN级别，这样既能获得必要信息，又不会生成太多不必要的日志数据。如果系统发生错误，可能需要临时提高日志级别到ERROR，以便快速定位问题。

### `logback`配置文件

将日志配置文件`logback.xml`放在`src/resources/`目录下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">

    <!--定义日志文件的存储地址 logs为当前项目的logs目录 还可以设置为../logs -->
    <property name="LOG_HOME" value="logs"/>

    <!--控制台日志， 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度,%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--文件日志， 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
    </appender>


    <!--myibatis log configure-->
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG"/>

    <!-- 日志输出级别 -->
    <root level="Debug">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>
```

为了让Logback能够正确地扫描并使用`logback.xml`配置文件，需要确保几件事情：

1. **配置文件位置：** `logback.xml` 应该位于资源文件夹（`resources`）下，这样它就会被包含在类路径（classpath）中。在标准的Gradle项目结构中，资源目录通常是 `src/main/resources`。
2. **确保资源被包含：** 在Gradle构建脚本中，确保`resources`目录被正确地包括在内。

```groovy
sourceSets {    // 定义源代码的目录块
    main { // 定义源代码和资源文件的目录
        java { srcDirs = ['src/main/java'] } //定义主源代码目录
        //定义主资源目录
        resources { srcDirs = ['src/main/resources'] }
    }
    test { // 定义测试源代码和资源的目录
        java { srcDirs = ['src/test/java'] }
        resources { srcDirs = ['src/test/resources'] }
    }
}
```
