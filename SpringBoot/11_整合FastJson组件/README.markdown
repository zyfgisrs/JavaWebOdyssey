# 整合FastJSON组件

FastJSON 是一个由阿里巴巴开发的 Java 库，用于解析 JSON 对象，包括序列化和反序列化。它是处理 JSON 格式数据的流行选择，因为它提供了高性能、易用性、丰富的功能和灵活性。

以下是 FastJSON 的一些主要特点：

1. **性能高效**：FastJSON 在性能方面经过优化，尤其是在序列化和反序列化操作方面，它被设计为快速和低开销。这对于性能敏感的应用程序或需要处理大量 JSON 数据的系统特别重要。

2. **丰富的功能**：
   - **数据转换**：提供简单直接的 API 来将常见的 Java 数据类型（如字符串、数字、集合等）转换为 JSON 字符串，反之亦然。
   - **对象模型**：支持将 JSON 字符串转换为 Java 对象，或者将 Java 对象转换为 JSON 字符串。
   - **过滤与修改**：允许在序列化和反序列化过程中过滤和修改属性。

3. **易用性**：FastJSON 的 API 相对直观，易于上手，这使得开发者可以快速在应用程序中实现 JSON 处理。

4. **支持复杂场景**：FastJSON 支持复杂的 Java 泛型场景，可以处理更加复杂的数据结构的序列化和反序列化。

5. **安全性**：FastJSON 提供了多种设置和工具来防止一些常见的安全问题，例如递归引用和远程代码执行等。然而，需要注意的是，过去曾有一些版本的 FastJSON 暴露了安全漏洞，因此，使用时需要确保使用的是最新且已经修复了已知安全问题的版本。

6. **配置灵活**：通过使用 SerializerFeature 类，开发者可以在序列化过程中自定义各种特性，比如输出空集合、格式化日期输出、输出类名等。

## 添加依赖

- 定义版本

```groovy
//定义版本号
ext.versions = [
        springboot           : '2.4.3', // SpringBoot版本
        junit                : '5.7.1', //配置junit测试工具的版本编号
        junitPlatformLauncher: '1.7.1',//JUnit测试工具运行平台
        lombok               : '1.18.30',
        fastjson             : '2.0.41'
]

//定义所有的依赖库
ext.libraries = [
        //SpringBoot项目所需要的核心依赖:
        'spring-boot-gradle-plugin': "org.springframework.boot:spring-boot-gradle-plugin:${versions.springboot}",
        //以下的配置为与项目用例测试有关的依赖:
        'junit-jupiter-api'        : "org.junit.jupiter:junit-jupiter-api:${versions.junit}",
        'junit-jupiter-engine'     : "org.junit.jupiter:junit-jupiter-engine:${versions.junit}",
        'junit-vintage-engine'     : "org.junit.vintage:junit-vintage-engine:${versions.junit}",
        'junit-bom'                : "org.junit:junit-bom:${versions.junit}",
        'junit-platform-launcher'  : "org.junit.platform:junit-platform-launcher:${versions.junitPlatformLauncher}",
        'lombok'                   : "org.projectlombok:lombok:${versions.lombok}",
        'fastjson'                 : "com.alibaba:fastjson:${versions.fastjson}"
]
```

- 添加依赖

```groovy
project('microboot-web') { // 子模块
    dependencies { // 配置子模块依赖
        compile(project(':microboot-common')) // 引入其他子模块
        compile(libraries.'fastjson')
    }
}
```

- 项目刷新

![image-20231020153853051](assets/image-20231020153853051.png)

## 配置FastJSON

- `【microboot-web】`子项目`com.zhouyf.config.WebConfig`

`WebConfig`配置类的目的是替换Spring默认的JSON处理库Jackson，改为使用阿里巴巴的FastJSON库，为HTTP消息转换提供更灵活的配置。

```java
package com.zhouyf.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@Configuration是一个注解，它表明随后的类是一个配置类，Spring容器会特别对待。
@EnableWebMvc
//主要作用是启用 Spring MVC 的配置
public class WebConfig implements WebMvcConfigurer {
    //这行代码定义了一个名为WebConfig的公共类，该类实现了WebMvcConfigurer接口。
    // 通过实现此接口，可以自定义Web应用程序中的多个配置。
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //重写了WebMvcConfigurer接口中的configureMessageConverters方法。
        // 这个方法用于自定义HTTP消息转换器。

        //移除Jackson组件,不使用默认的Jackson JSON处理库。
        for(int x = 0; x < converters.size(); x++){
            if(converters.get(x) instanceof MappingJackson2HttpMessageConverter){
                converters.remove(x);
            }
        }
        //创建FastJsonHttpMessageConverter的一个实例。
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter =
                new FastJsonHttpMessageConverter();
        //创建FastJsonConfig的一个实例，以便能够自定义JSON的序列化和反序列化配置。
        FastJsonConfig config = new FastJsonConfig();
        //这段代码调用setSerializerFeatures方法来设置各种JSON序列化选项
        // 例如如何处理空值、日期格式、循环引用等。
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,//允许Map内容为null
                SerializerFeature.WriteNullListAsEmpty,//List集合为null则使用[]代替
                SerializerFeature.WriteNullStringAsEmpty,//String内容为空使用空字符代替
                SerializerFeature.WriteDateUseDateFormat, //日期格式化输出
                SerializerFeature.WriteNullNumberAsZero, //数字为空使用0
                SerializerFeature.DisableCircularReferenceDetect //禁用循环引用
        );
        //将自定义的FastJsonConfig配置设置到fastJsonHttpMessageConverter转换器实例中。
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        //转换器将仅用于JSON类型的数据
        List<MediaType> fastjsonMediaTypes = new ArrayList<>();
        fastjsonMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastjsonMediaTypes);
        //这行代码将配置好的FastJSON消息转换器添加到转换器列表中。
        // 这确保了Spring会使用这个转换器来处理JSON消息。
        converters.add(fastJsonHttpMessageConverter);
    }
}
```

在这段代码中，`@EnableWebMvc` 注解的主要作用是启用 Spring MVC 的配置。这意味着它会告诉 Spring，这个应用程序是一个 Web 应用程序，应该启用与 Spring MVC 相关的一些核心功能和默认配置。但是，它也有另一个重要影响：它会停用 Spring Boot 对 Spring MVC 的自动配置。

在标准的 Spring Boot 应用程序中，Spring MVC 是自动配置的，这包括默认注册一系列的 `HttpMessageConverter` 实例，这些实例负责在 HTTP 请求和响应之间序列化和反序列化数据。其中之一是 `MappingJackson2HttpMessageConverter`，它用于处理 JSON 数据，并默认使用 Jackson 库来序列化和反序列化对象。

然而，在这个特定的配置类 `WebConfig` 中，作者显然想要改用 FastJSON 来处理 JSON 消息，而不是默认的 Jackson。这就是为什么他们重写了 `configureMessageConverters` 方法，并手动删除了 `MappingJackson2HttpMessageConverter` 实例，然后添加了自己配置的 `FastJsonHttpMessageConverter` 实例。

总的来说，在这段代码中，`@EnableWebMvc` 注解的作用是：

1. 启用 Spring MVC：激活 Spring MVC 相关的核心功能和默认配置，如 `DispatcherServlet`、处理器映射和处理器适配器等。

2. 停用自动配置：覆盖 Spring Boot 的自动配置，使得开发者可以完全控制 Spring MVC 的行为。在这种情况下，这允许他们更改默认的消息转换器。

3. 允许自定义配置：通过实现 `WebMvcConfigurer` 接口的方法，提供了一个机制来自定义 Web 应用程序的配置。在这个例子中，这是通过自定义 HTTP 消息转换器实现的。

- 修改`Messgae`

```java
package com.zhouyf.vo;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String title;
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubdate;
    private String content;
}
```

- 启动springboot并访问服务器

```
http://localhost:8080/message/echo?title=zhouyf&pubdate=2021-11-11&content=zhouyangfan
```

![image-20231020165415810](assets/image-20231020165415810.png)

FastJSON成功对对消息进行了转换。

