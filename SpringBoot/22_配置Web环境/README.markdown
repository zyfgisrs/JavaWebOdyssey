# 配置Web环境

## profile环境配置

在Spring和Spring Boot应用中，环境配置的概念是非常重要的。特别是"Profile"这一特性，它允许开发者为不同的运行环境定义和组织不同的配置。

以下是Profile环境配置的主要作用：

1. **环境区分**：在软件开发过程中，通常会有多个环境，例如开发环境、测试环境、生产环境等。每个环境可能有其特定的配置，如数据库连接、服务URL、特定的功能开关等。使用Profile，你可以为每个环境创建一个特定的配置文件。

2. **条件激活Bean**：你可以使用`@Profile`注解在Spring配置中为特定的Profile条件性地创建beans。这意味着某些beans只会在特定的Profile被激活时才会被创建。

3. **易于管理和切换**：在开发过程中，你可能需要在不同的Profile之间切换，以测试应用程序在不同环境下的行为。Spring提供了简单的方法来激活和切换不同的Profile。

4. **增强代码的可读性和组织性**：通过将配置分隔到不同的Profile中，你可以更清晰地组织和管理代码。这使得配置更加模块化，并帮助开发者快速理解每个环境的特定配置。

5. **避免硬编码**：Profile可以帮助避免将特定环境的配置硬编码到应用程序中。这不仅增强了代码的灵活性，还使得维护和修改配置变得更加简单。

6. **灵活性**：Profile不仅限于环境配置。你可以根据需要定义任何数量的Profile，以满足特定的用例或需求。

要使用Profile，你可以在Spring配置文件中使用`<beans profile="...">`元素或在Java配置中使用`@Profile`注解。在Spring Boot中，你可以为每个Profile创建一个命名为`application-{profile}.properties`或`application-{profile}.yml`的文件。

### 定义profile文件

- `application.yml`

```yaml
spring:
  profiles:
    active: dev #默认为开发环境
```

- `application-dev.yml`

```yaml
server:
  port: 8080
  servlet:
    context-path: /dev
```

- `application-test.yml`

```yaml
server:
  port: 8181
  servlet:
    context-path: /test
```

- `applicat-production.yml`

```yaml
server:
  port: 8282
  servlet:
    context-path: /product
```

启动SpringBoot，可以看到端口为8080，上下文路径为`/dev`

![image-20231027100457822](assets/image-20231027100457822.png)

将环境修改为生产环境：

```yaml
spring:
  profiles:
    active: product
```

再次启动SpringBoot，可以看到端口号为8282，上下文路径为`/product`

![image-20231027100759164](assets/image-20231027100759164.png)

### 使用JAR启动时指定环境

- 先把microboot-web打成JAR文件

- `SpringBoot\23_配置Web环境\microboot\microboot-web\build\libs`

使用dev环境启动项目：

```
java -jar zhouyfboot-1.0.1-zhou.jar --spring.profiles.active=dev
```

![image-20231027101502683](assets/image-20231027101502683.png)

使用product环境启动项目：

```
java -jar zhouyfboot-1.0.1-zhou.jar --spring.profiles.active=product
```

![image-20231027101643496](assets/image-20231027101643496.png)

## WAR文件部署

将Spring Boot应用程序打包为WAR文件并部署到Tomcat中

- 添加使用打包为WAR的插件

```groovy
plugins {
    id 'java'
    id 'war'
}
```

- 在依赖项中添加`spring-boot-starter-tomcat`，并将其设置为`providedRuntime`，这样它就不会被包含在生成的WAR文件中，因为Tomcat已经提供了这个依赖：

![image-20231027130125358](assets/image-20231027130125358.png)

- Spring Boot应用程序的主类需要扩展`SpringBootServletInitializer`并覆盖：

```java
package com.zhouyf;

import com.zhouyf.banner.MyBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(MyApplication.class);//获取实例化对象
        springApplication.setBanner(new MyBanner());//配置自定义Banner的生成器
        springApplication.setBannerMode(Banner.Mode.CONSOLE); //配置Banner输出到控制台
        springApplication.run(args); //运行SpringBoot程序
    }
}
```

- 运行bootWar任务

<img src="assets/image-20231027130246596.png" alt="image-20231027130246596" style="zoom:50%;" />

- 将生成的WAR文件复制到Tomcat的`webapps`目录中。

![image-20231027130331772](assets/image-20231027130331772.png)

- 启动tomcat服务器

![image-20231027130416126](assets/image-20231027130416126.png)

- 项目已经启动：

![image-20231027130535850](assets/image-20231027130535850.png)

- 访问测试

```
http://localhost:8080/microboot-web-1.0.0/dept/show
```

![image-20231027130610318](assets/image-20231027130610318.png)