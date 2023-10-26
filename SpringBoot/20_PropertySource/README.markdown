



# @PropertySource

`@PropertySource` 是一个 Spring 注解，用于指示 Spring 加载一个或多个属性文件并将它们添加到 Spring 环境的属性源中。这样，您可以在 Spring 上下文中使用这些属性，例如通过 `@Value` 注解将属性值注入到 Spring beans 中。

以下是 `@PropertySource` 的主要功能和使用方法：

1. **加载额外的属性文件**：
   默认情况下，Spring Boot 会自动加载 `application.properties` 或 `application.yml` 文件。但有时您可能需要从其他自定义属性文件中加载配置，这时就可以使用 `@PropertySource`。

2. **指定属性文件的路径**：
   您可以使用类路径、文件系统路径或 URL 来指定属性文件的位置。

   ```java
   @PropertySource("classpath:custom-config.properties")
   ```

3. **支持占位符**：
   在 `@PropertySource` 的值中，您可以使用 `${...}` 占位符来指定动态的文件路径。

   ```java
   @PropertySource("classpath:config-${spring.profiles.active}.properties")
   ```

   上述示例中，根据当前活跃的 Spring profile，会加载相应命名的配置文件。

4. **加载多个属性文件**：
   您可以加载多个属性文件，只需在 `@PropertySource` 中指定它们即可。

   ```java
   @PropertySource({"classpath:config1.properties", "classpath:config2.properties"})
   ```

5. **文件编码**：
   从 Spring 5.2 开始，`@PropertySource` 注解有一个 `encoding` 属性，允许您指定属性文件的字符编码。

   ```java
   @PropertySource(value = "classpath:custom-config.properties", encoding = "UTF-8")
   ```

6. **与 `Environment` 一起使用**：
   加载到 Spring 环境的属性可以使用 `Environment` 接口进行访问。

   ```java
   @Autowired
   private Environment env;
   
   public String getCustomProperty() {
       return env.getProperty("custom.property.name");
   }
   ```

注意：虽然 `@PropertySource` 可以加载 `.properties` 和 `.yml` 文件，但对于 `.yml` 文件，它只支持简单的属性结构，并不支持 YAML 的高级特性。如果您需要处理复杂的 YAML 结构，建议使用 Spring Boot 的 `@ConfigurationProperties`。

- `company.properties`

```properties
company.cid=10000
company.cname=阿里巴巴
```

- `com.zhouyf.vo.Company`

```java
package com.zhouyf.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Company {
    @Value("${company.cid}")
    private Long cid;

    @Value("${company.cname}")
    private String cname;
}
```

- 配置类`com.zhouyf.config.CompanyConfig`

```java
package com.zhouyf.config;

import com.zhouyf.vo.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:company.properties",encoding = "UTF-8")
public class CompanyConfig {
    @Bean
    public Company getCompany(){
        return new Company();
    }
}
```

- `com.zhouyf.action.CompanyAction`

```java
package com.zhouyf.action;

import com.zhouyf.vo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/*")
public class CompanyAction {
    @Autowired
    private Company company;

    @GetMapping(name = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company show(){
        return company;
    }
}
```

- 访问测试

![image-20231025204014365](assets/image-20231025204014365.png)