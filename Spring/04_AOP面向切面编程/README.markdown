# AOP面向切面编程

> 面向切面编程（AOP）是一个重要的编程范式，它允许开发者将横切关注点（cross-cutting concerns）从业务逻辑中分离出来，提高代码的模块化。在Spring框架中，AOP 是核心组件之一，通过它可以实现如日志记录、事务管理、安全性等功能。

## 相关术语

- 切面（`Aspect`）：切面是`AOP`的基础。它将那些影响多个类的行为（例如日志、安全性等）封装起来，主要目的是增加模块化。你可以将切面视为关注点的模块化，这个关注点可能会横切多个对象和模块。
- 连接点（`Join Point`）：连接点是程序执行过程中的某个特定点，比如方法调用时或异常抛出时。在`Spring AOP`中，一个连接点总是代表一个方法的执行。
- 通知（`Advice`）：通知定义了切面是什么以及何时使用。它是你在某个特定的连接点上执行的动作。Spring 划分通知为几个类型，包括：
  - **前置通知（Before advice）**：在某连接点之前执行，但不能阻止执行流程继续进行，除非它抛出一个异常。
  - **后置通知（After returning advice）**：在方法成功执行后执行。
  - **异常通知（After throwing advice）**：如果方法抛出异常，则会执行该通知。
  - **最终通知（After (finally) advice）**：无论方法执行是否成功，该通知都会执行。
  - **环绕通知（Around advice）**：围绕方法执行，可以在方法执行前后自定义行为，甚至阻止方法的执行。

- 切入点（`Pointcut`）：切入点定义了在哪些连接点执行切面的通知。它是用来精确指定通知应该被触发的方法的执行点的表达式。通过切入点表达式，你可以指定特定的类或者方法。在`Spring AOP`中，切入点表达式通常使用`AspectJ`的语法来定义。
- 引入（`Introduction`）：引入允许我们向现有的类添加新的方法或属性。这是一种设计模式，可以在不改变现有类代码的情况下，为其增加新的行为和状态。
- 织入（`Weaving`）：织入是将切面与其他对象或函数连接（或“织入”）起来的过程，这可以在编译时（例如使用`AspectJ`编译器）、加载时或运行时（例如`Spring AOP`）发生。在Spring框架中，织入通常在运行时通过代理对象来完成。

## 配置`AOP`环境

```groovy
dependencies {
    //如果需要使用AspectJ的注解方式则需要引入spring-aspects
    implementation group: 'org.springframework', name: 'spring-aspects', version: '6.0.7'
}
```

## `AOP`实现

- `com.zhouyf.pojo.Message`

```java
package com.zhouyf.pojo;

import java.util.Date;

public class Message {
    private String title;

    private String name;

    private Date pubdate;

    public Message(String title, String name, Date date){
        this.name = name;
        this.title = title;
        this.pubdate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", pubdate=" + pubdate +
                '}';
    }
}
```

- 接口`com.zhouyf.service.MessageService`与实现类`com.zhouyf.service.impl.MessageServiceImpl`

```java
package com.zhouyf.service;

public interface MessageService {
    void echo();
}
```

```java
package com.zhouyf.service.impl;

import com.zhouyf.pojo.Message;
import com.zhouyf.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private Message message;

    @Override
    public void echo() {
        System.out.println("【消息推送】" + message);
    }
}
```

- 配置类`com.zhouyf.config.MessageConfig`

```java
package com.zhouyf.config;

import com.zhouyf.pojo.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
@EnableAspectJAutoProxy()
//启用 @AspectJ 支持
public class MessageConfig {
    @Bean
    public Message getMessage() throws ParseException {
        return new Message("巴以冲突", "zhouyf",
                new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-01"));
    }
}
```

- 切面`com.zhouyf.aspect.ServiceAspect`

```java
package com.zhouyf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    @Before("execution(* com.zhouyf.service.impl.MessageServiceImpl.echo(..))")
    public void beforeEchoMethod(JoinPoint joinPoint) {
        System.out.println("Before executing echo method: " + joinPoint.getSignature().getName());
    }
}
```

- 测试类`com.zhouyf.test.MessageServiceTest`

```java
package com.zhouyf.test;


import com.zhouyf.aspect.ServiceAspect;
import com.zhouyf.config.MessageConfig;
import com.zhouyf.service.MessageService;
import com.zhouyf.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MessageServiceImpl.class, ServiceAspect.class, MessageConfig.class})
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test
    public void test() {
        messageService.echo();
    }
}

```

- 控制台打印

```
Before executing echo method: echo
【消息推送】Message{title='巴以冲突', name='zhouyf', pubdate=Wed Nov 01 00:00:00 CST 2023}
```

以上AOP的实现使用了`AspectJ`注解：

- 在Spring框架中，AOP（面向切面编程）的实现主要是通过`spring-aop`模块来完成的。`spring-aop`提供了基于代理的AOP支持，使得我们可以定义如方法拦截和方法前后执行等逻辑。
- `AspectJ`是一个独立的、功能更强大的AOP框架，它提供了比`Spring AOP`更多的功能，如构造函数拦截、静态方法拦截等，并且它的织入（weaving）过程可以在编译时（compile-time）、类加载时（load-time）或者运行时（runtime）进行。
- 在`Spring AOP`中使用`AspectJ`注解是为了更方便地定义切面、通知和切点。通过`@Aspect`注解，我们可以轻松地声明一个类作为切面，并使用`@Before`、`@After`、`@Around`等注解来定义通知（Advice），以及通过`@Pointcut`注解来定义切点（Pointcut）。实际的代理仍然是由`spring-aop`模块在运行时创建的。
- 当我们说使用`AspectJ`注解来实现`Spring AOP`的时候，我们是指使用`AspectJ`的注解风格和语法来定义切面和通知，但在底层，AOP的逻辑是通过`Spring AOP`的代理机制来实现的。

##  `execution` 表达式

基本语法

```
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
```

- `modifiers-pattern`：方法的修饰符（如 `public`），`?` 表示这个部分是可选的。
- `ret-type-pattern`：方法的返回类型，可以使用 `*` 作为通配符。
- `declaring-type-pattern`：方法所在类的全路径名，也可以使用 `*` 作为通配符，`?` 表示这个部分是可选的。
- `name-pattern`：方法名，可以使用 `*` 作为通配符。
- `param-pattern`：方法参数列表，可以使用 `..` 表示任意数量、任意类型的参数。
- `throws-pattern`：方法抛出的异常类型，`?` 表示这个部分是可选的。

**匹配指定类中的所有方法**：

```
execution(* com.example.service.UserService.*(..))
```

**匹配指定包下所有类的所有方法**：

```
execution(* com.example.service..*.*(..))
```

- `execution()`: 这个关键字表示某个方法的执行时刻会触发。这是定义切点的最常见方式，用于匹配方法执行的连接点。
- `*`: 这是一个通配符，表示匹配任何返回类型的方法。这里它用来表示无论方法返回值是什么类型都会被匹配。
- `com.example.service`: 这指定了包名。它会匹配`com.example.service`这个包中的类。
- `..`: 当用在包名后面时，这个通配符表示匹配指定包及其子包下的所有类。在方法参数列表中，它表示匹配任意数量（包括零个）的参数。
- `*`: 当用在类名后面时，这个通配符表示匹配包中的所有类。
- `.*`: 这里的点`.`表示属于类的方法。紧随其后的星号`*`表示匹配这些类中的所有方法。
- `(..)`: 在方法名后面的括号中使用两个点`..`，表示匹配方法的任意参数列表。这意味着无论方法有多少参数，或者是什么类型的参数，都将被这个切点表达式匹配。

**匹配`com.example.service`包下所有类的私有方法，并且这些方法的返回类型为`String`**：

```
execution(private String com.example.service..*.*(..))
```

## 通知的执行顺序

- POJO类`com.zhouyf.pojo.Author`和`com.zhouyf.pojo.Book`

```java
package com.zhouyf.pojo;

public class Author {
    private String name;

    public Author(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

```java
package com.zhouyf.pojo;

public class Book {
    private Author author;

    private String name;


    public Book(Author author, String name) {
        this.author = author;
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", name='" + name + '\'' +
                '}';
    }
}
```

- Service接口与Service实现类

```java
package com.zhouyf.service;

import com.zhouyf.pojo.Book;

public interface BookService {
    boolean remove();

    void show();

    void add(Book book);
}
```

```java
package com.zhouyf.service.impl;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private Book book;

    @Override
    public boolean remove() {
        System.out.println("删除成功");
        return true;
    }

    @Override
    public void show() {
        System.out.println(book);
    }

    @Override
    public void add(Book book) {
        System.out.println("添加了一本书");
    }
}
```

- 切面类`com.zhouyf.aspect.BookServiceAspect`

```java
package com.zhouyf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class BookServiceAspect {
    @Pointcut("execution(* com.zhouyf.service..*.*(..))")
    public void bookServiceLayer(){

    }

    @Before("bookServiceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("【@Before】logBefore() is running!");
        System.out.println("【@Before】Intercepted : " + joinPoint.getSignature().getName());
    }

    // 通知（环绕通知）
    @Around("bookServiceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("【@Around】Around before is running!");
        System.out.println("【@Around】Method : " + joinPoint.getSignature().getName());
        System.out.println("【@Around】Arguments : " + Arrays.toString(joinPoint.getArgs()));
        Object returnValue = joinPoint.proceed();  // 继续执行连接点方法
        System.out.println("【@Around】" + returnValue);
        System.out.println("【@Around】Around after is running!");
        return returnValue;
    }

    @AfterReturning(pointcut = "bookServiceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("【@AfterReturning】logAfterReturning() is running!");
        System.out.println("【@AfterReturning】Method returned value is : " + result);
    }

    @After("bookServiceLayer()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("【@After】logAfter is running!");
    }
}
```

- 配置类`com.zhouyf.config.BookConfig`

```java
package com.zhouyf.config;

import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy()
@Configuration
@ComponentScan(basePackages = {"com.zhouyf.service", "com.zhouyf.aspect"})
public class BookConfig {
    @Bean
    public Book bookBean(){
        return new Book(authorBean(), "格林童话");
    }

    @Bean
    public Author authorBean(){
        return new Author("格林");
    }
}
```

- 测试类

```java
package com.zhouyf.test;

import com.zhouyf.config.BookConfig;
import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import com.zhouyf.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookConfig.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void test(){
        bookService.remove();
        bookService.show();
        bookService.add(new Book(new Author("鲁迅"), "朝花夕拾"));
    }
}
```

执行`remove()`方法：

```
【@Around】Around before is running!
【@Around】Method : remove
【@Around】Arguments : []
【@Before】logBefore() is running!
【@Before】Intercepted : remove
删除成功
【@AfterReturning】logAfterReturning() is running!
【@AfterReturning】Method returned value is : true
【@After】logAfter is running!
【@Around】true
【@Around】Around after is running!
```

- `joinPoint.getSignature().getName()`返回方法的签名
- `Arrays.toString(joinPoint.getArgs())`获取方法的参数列表
- `joinPoint.proceed()`返回方法的返回值

执行`show()`方法：

```
【@Around】Around before is running!
【@Around】Method : show
【@Around】Arguments : []
【@Before】logBefore() is running!
【@Before】Intercepted : show
Book{author=Author{name='格林'}, name='格林童话'}
【@AfterReturning】logAfterReturning() is running!
【@AfterReturning】Method returned value is : null
【@After】logAfter is running!
【@Around】null
【@Around】Around after is running!
```

执行`add()`方法：

```
【@Around】Around before is running!
【@Around】Method : add
【@Around】Arguments : [Book{author=Author{name='鲁迅'}, name='朝花夕拾'}]
【@Before】logBefore() is running!
【@Before】Intercepted : add
添加了一本书
【@AfterReturning】logAfterReturning() is running!
【@AfterReturning】Method returned value is : null
【@After】logAfter is running!
【@Around】null
【@Around】Around after is running!
```

- 显示了方法的参数 `[Book{author=Author{name='鲁迅'}, name='朝花夕拾'}]`

### 通知的执行顺序总结

Around->Before->方法本身->AfterReturning->After->Around

