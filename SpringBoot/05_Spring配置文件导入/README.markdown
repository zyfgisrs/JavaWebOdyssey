# Spring配置文件导入

## 使用注解注入Bean

【**microboot项目->microboot-web子模块**】

- 创建业务接口`com.zhouyf.service.ImessageService`

```java
package com.zhouyf.service;

public interface ImessageService { // 创建业务接口
    public String echo(String msg);
}
```

- 定义业务接口的子类`com.zhouyf.service.impl.messageServiceImpl`

```java
package com.zhouyf.service.impl;

import com.zhouyf.service.ImessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements ImessageService {
    @Override
    public String echo(String msg) {
        return "[ECHO]" + msg;
    }
}
```

- 创建`com.zhouyf.action.MessageAction`，实现IOC的依赖注入

```java
package com.zhouyf.action;

import com.zhouyf.service.ImessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/*") //父类路径
public class MessageAction {
    @Autowired
    private ImessageService messageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAction.class);
    
    @RequestMapping("echo")
    public String echo(String msg){
        LOGGER.info("接受到的msg请求参数:{}",msg); //日志输出
        return this.messageService.echo(msg); //直接Rest响应
    }
}
```

- 启动项目进行访问

  ![image-20231009152243871](assets/image-20231009152243871.png)

## 使用Spring配置文件

【**microboot项目->microboot-web子模块**】

- 定义业务接口的子类`com.zhouyf.service.impl.messageServiceImpl`，不使用@Service注解

```java
package com.zhouyf.service.impl;

import com.zhouyf.service.ImessageService;

public class MessageServiceImpl implements ImessageService {
    @Override
    public String echo(String msg) {
        return "[ECHO]" + msg;
    }
}
```

- 创建配置文件夹`microboot\microboot-web\src\main\resources\META-INFO\spring`
- 创建配置文件`spring-service.xml`

<img src="assets/image-20231009153027502.png" alt="image-20231009153027502" style="zoom:80%;" />

- 编写配置文件，向spring容器中注册Bean对象

```xml
<bean id="messageService" class="com.zhouyf.service.impl.MessageServiceImpl"/>
```

- SpringBoot整合Spring配置文件，即向启动类中追加相应的配置注解I`mportResource`，这个注解引入要导入的spring配置文件

  ```java
  package com.zhouyf;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.context.annotation.ImportResource;
  
  
  @SpringBootApplication
  @ImportResource(locations = {"classpath:META-INFO/spring/spring-*.xml"}) 
  public class MyApplication {
      public static void main(String[] args) {
          SpringApplication.run(MyApplication.class, args);
      }
  }
  ```

- 启动项目进行访问

  ![image-20231009153910091](assets/image-20231009153910091.png)

成功回显

## @ImportResource

在Spring Boot中，`@ImportResource` 注解用于导入传统的Spring配置文件（通常是XML格式），这在某些场景下是非常有用的，特别是当你在Spring Boot项目中需要使用一些只能通过XML配置的特性或库时。

### 基本用法

在Spring Boot应用中使用`@ImportResource`注解，你可以将XML配置文件导入到使用`@SpringBootApplication`或`@Configuration`注解的Java配置类中。例如：

```java
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:some-context.xml")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

在上述示例中，`some-context.xml`是一个Spring XML配置文件，它被放在类路径（classpath）上。这个文件中定义的所有bean和配置都将被添加到Spring Boot应用的上下文中。

### 使用场景

虽然Spring Boot强烈推荐使用Java配置（通过Java `@Configuration` 类），但在以下情况下，你可能会发现`@ImportResource`注解非常有用：

- **迁移旧项目**：当你正在将一个基于Spring的项目迁移到Spring Boot时，而该项目大量使用了XML配置。
  
- **使用第三方库**：当你使用的一些第三方库或框架要求使用XML进行配置时。

- **混合配置**：在一些复杂的项目中，你可能希望使用Java配置和XML配置的混合方式。

### 注意事项

- 确保XML配置文件位于类路径上，或者你提供了正确的文件系统路径。
  
- 如果你有多个XML配置文件，你可以将它们作为一个字符串数组传递给`@ImportResource`。例如：
  ```java
  @ImportResource({"classpath:some-context.xml", "classpath:another-context.xml"})
  ```
- 在使用`@ImportResource`时，要确保你的项目依赖中包含了Spring的XML模式文件（通常，添加`spring-context`依赖到你的构建配置中就足够了）。

尽管`@ImportResource`在某些情况下是必要的或有用的，但如果可能的话，尽量坚持使用Java配置，因为它提供了类型安全和更多的重构能力。

