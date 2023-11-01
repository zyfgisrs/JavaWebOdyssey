# @ConfigrationProperties

`@ConfigurationProperties` 是 Spring Boot 中的一个注解，它允许您将配置文件中的属性值绑定到 Java 对象。使用这个注解，您可以轻松地将配置文件中的属性组映射到一个带有字段的 POJO (Plain Old Java Object)，而不必为每个属性单独使用 `@Value` 注解。

以下是 `@ConfigurationProperties` 的一些主要功能和优点：

1. **批量绑定**：允许您将配置文件中的一个属性前缀下的所有属性批量绑定到一个 Java 对象的字段上。

2. **类型安全**：为配置属性提供类型安全的绑定，这意味着如果配置文件中的值类型与 Java 对象的字段类型不匹配，您会在启动时得到一个错误。

3. **更好的代码组织**：您可以将相关的配置属性组织在一个类中，这样会使代码更加整洁。

4. **嵌套属性支持**：支持配置的嵌套属性，使您可以将复杂的配置结构映射到 Java 对象的层次结构。

5. **验证和元数据生成**：与 `@Valid` 注解结合使用时，可以对配置属性进行验证。此外，Spring Boot 提供了工具来生成配置属性的元数据，这有助于自动完成和文档生成。

6. **自定义转换和适配**：可以为特定的字段或数据类型定义自定义的转换逻辑。

**示例**：

假设您有以下 `application.yml` 配置：

```yaml
app:
  name: My Application
  description: This is a sample application
  metadata:
    version: 1.0
    authors:
      - Alice
      - Bob
```

您可以使用 `@ConfigurationProperties` 创建一个 Java 类来映射这些属性：

```java
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String description;
    private Metadata metadata;

    // getters and setters ...

    public static class Metadata {
        private String version;
        private List<String> authors;

        // getters and setters ...
    }
}
```

要使 `@ConfigurationProperties` 生效，您还需要在一个配置类上添加 `@EnableConfigurationProperties` 注解，并引用您的属性类：

```java
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
}
```

- `application.yaml`

```yaml
source:
  mysql: mysql://localhost:3306/zhouyf
  redis: redis://localhost:6379/0
  messages:
    - JAVA
    - C++
    - RUST
  info:
    javabase: java基础
    spring: java框架
    jvm: java虚拟机
```

- `com.zhouyf.vo.Source`

```java
package com.zhouyf.vo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
@Data
@ConfigurationProperties(prefix = "source")
public class Source {
    private String mysql;

    private String redis;

    private List<String> messages;

    private Map<String, String> info;
}
```

- `com.zhouyf.config.SourceConfig`

```java
package com.zhouyf.config;


import com.zhouyf.vo.Source;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class SourceConfig {

    @Bean
    public Source createSource(){
        return new Source();
    }
}
```

- `com.zhouyf.action.SourceAction`

```java
package com.zhouyf.action;

import com.zhouyf.vo.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source/*")
public class SourceAction {
    @Autowired
    private Source source;

    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Source showSource() {
        return this.source;
    }
}
```

- 访问测试

```
localhost:8080/source/show
```

```json
{
  "info": {
    "javabase": "java基础",
    "spring": "java框架",
    "jvm": "java虚拟机"
  },
  "messages": [
    "JAVA",
    "C++",
    "RUST"
  ],
  "mysql": "mysql://localhost:3306/zhouyf",
  "redis": "redis://localhost:6379/0"
}
```

![image-20231025153012697](assets/image-20231025153012697.png)

`@EnableConfigurationProperties` 是 Spring Boot 中的一个注解，用于启用对 `@ConfigurationProperties` 注解类的支持。它确保将配置属性绑定到带有 `@ConfigurationProperties` 注解的类。

以下是 `@EnableConfigurationProperties` 注解的主要功能和用途：

1. **注册 `@ConfigurationProperties` Beans**：当您在一个配置类上使用 `@EnableConfigurationProperties` 注解，并为其提供一个或多个 `@ConfigurationProperties` 类作为参数，Spring Boot 会自动为这些类创建 bean，并确保这些 bean 可以从环境属性中接收值。
2. **简化配置**：使用 `@EnableConfigurationProperties` 可以避免手动注册带有 `@ConfigurationProperties` 的 bean。这为开发者提供了一个方便的方式，使他们可以轻松地在应用程序中使用类型安全的配置属性。
3. **与 `@Configuration` 类结合使用**：通常，`@EnableConfigurationProperties` 与 `@Configuration` 注解一起使用，这样可以在应用程序的上下文中注册并启用特定的配置属性类。

# 对象注入

- 添加vo类

```java
package com.zhouyf.vo;

import lombok.Data;

@Data
public class Company {
    private Long cid;

    private String cname;
}
```

```java
package com.zhouyf.vo;

import lombok.Data;

@Data
public class Emp {
    private Long empno;

    private String ename;

    private String job;
}
```

```java
package com.zhouyf.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
@Data
@ConfigurationProperties(prefix = "dept")

public class Dept {
    private Long deptno;

    private String dname;

    private Company company;

    private List<Emp> emps;
}
```

- 配置类c`om.zhouyf.config.DeptConfig`

```java
package com.zhouyf.config;

import com.zhouyf.vo.Dept;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties

public class DeptConfig {
    @Bean
    public Dept createDept() {
        return new Dept();
    }
}
```

- `application.yaml`

```yaml
dept:
  deptno: 10000
  dname: 教学研发部门
  company:
    cid: 10001
    cname: 新东方
  emps:
    - emp:
      empno: 7389
      ename: 小明
      job: 教师
    - emp:
      empno: 9889
      ename: 王航
      job: 工程师
```

注意缩进问题，否则无法进行解析

- `com.zhouyf.action.DeptAction`

```java
package com.zhouyf.action;

import com.zhouyf.vo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept/*")
public class DeptAction {

    @Autowired
    private Dept dept;

    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dept showDept(){
        return dept;
    }
}
```

- 访问测试

```
localhost:8080/dept/show
```

```json
{
  "company": {
    "cid": 10001,
    "cname": "新东方"
  },
  "deptno": 10000,
  "dname": "教学研发部门",
  "emps": [
    {
      "empno": 7389,
      "ename": "小明",
      "job": "教师"
    },
    {
      "empno": 9889,
      "ename": "王航",
      "job": "工程师"
    }
  ]
}
```

![image-20231025163110061](assets/image-20231025163110061.png)

# 注意点

当使用 `@ConfigurationProperties` 注解注入属性时，有一些关键点和最佳实践需要注意：

1. **前缀设置**：使用 `prefix` 属性来指定配置文件中的哪一部分应该与该类绑定。例如，`@ConfigurationProperties(prefix = "app")` 会匹配配置文件中以 `app` 为前缀的属性。

2. **类型安全**：由于 `@ConfigurationProperties` 提供类型安全的属性绑定，因此确保您的目标字段的类型与配置文件中的属性值匹配。如果不匹配，将在启动时抛出异常。

3. **字段命名与配置键**：Java 字段的命名与配置文件中的属性键有一个默认的转换规则。例如，Java 字段 `userName` 对应于配置中的 `user-name`。

4. **验证**：
   - 可以使用 Java Bean Validation API（例如 `@NotNull`、`@Size` 等注解）对类的字段进行验证。
   - 为了启用验证，需要在类路径中包含 Bean Validation 的实现（例如 Hibernate Validator）并在 `@ConfigurationProperties` 类上使用 `@Validated` 注解。

5. **嵌套属性**：可以在 `@ConfigurationProperties` 类中使用嵌套的静态类来表示嵌套的配置属性。

6. **列表和映射**：`@ConfigurationProperties` 支持列表和映射的绑定。例如，可以将配置中的属性列表绑定到 Java `List` 或 `Set`，并将属性映射绑定到 Java `Map`。

7. **Setter 方法**：确保为 `@ConfigurationProperties` 类中的所有字段提供公共的 setter 方法，因为 Spring Boot 使用这些方法来注入属性值。

8. **默认值**：可以为字段设置默认值，这样当配置文件中没有相应的属性或其值为空时，字段将使用其默认值。

9. **位置**：确保 `@ConfigurationProperties` 类位于一个被 Spring 管理的包内，或者确保您使用了 `@EnableConfigurationProperties` 注解来注册它。

10. **文档化**：考虑使用 `spring-boot-configuration-processor` 依赖来自动生成配置属性的元数据。这有助于提供 IDE 中的自动完成和属性描述。

11. **敏感属性**：如果您的 `@ConfigurationProperties` 类包含敏感信息（如密码），可以使用 `@JsonIgnore` 注解来防止其被意外暴露。

12. **环境属性**：`@ConfigurationProperties` 会从所有的环境属性源（如系统属性、环境变量、配置文件等）中获取属性值。确保您了解属性值的优先级和覆盖规则。

13. **不要与 `@Value` 混淆**：虽然 `@Value` 也用于从配置中注入值，但它更适用于简单的单值属性。对于结构化和复杂的配置，`@ConfigurationProperties` 更为推荐。

总之，当使用 `@ConfigurationProperties` 注解时，确保了解其工作机制、限制和最佳实践，以确保正确、安全地从配置中注入属性值。