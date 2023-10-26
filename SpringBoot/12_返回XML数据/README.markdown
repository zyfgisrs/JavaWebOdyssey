# 返回XML数据

## 添加依赖库

- 需要的依赖：

```
implementation group: 'com.fasterxml.jackson.dataformat', name: 'jackson-dataformat-xml', version: '2.12.2'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.12.2'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.12.2'
```

- 定义依赖版本

```groovy
//定义版本号
ext.versions = [
        springboot           : '2.4.3', // SpringBoot版本
        junit                : '5.7.1', //配置junit测试工具的版本编号
        junitPlatformLauncher: '1.7.1',//JUnit测试工具运行平台
        lombok               : '1.18.30',
        fastjson             : '2.0.41',
        jackson              : '2.12.2'
]

//定义所有的依赖库
ext.libraries = [
        //SpringBoot项目所需要的核心依赖:
        'spring-boot-gradle-plugin' : "org.springframework.boot:spring-boot-gradle-plugin:${versions.springboot}",
        //以下的配置为与项目用例测试有关的依赖:
        'junit-jupiter-api'         : "org.junit.jupiter:junit-jupiter-api:${versions.junit}",
        'junit-jupiter-engine'      : "org.junit.jupiter:junit-jupiter-engine:${versions.junit}",
        'junit-vintage-engine'      : "org.junit.vintage:junit-vintage-engine:${versions.junit}",
        'junit-bom'                 : "org.junit:junit-bom:${versions.junit}",
        'junit-platform-launcher'   : "org.junit.platform:junit-platform-launcher:${versions.junitPlatformLauncher}",
        //lombok
        'lombok'                    : "org.projectlombok:lombok:${versions.lombok}",
        //FastJSON
        'fastjson'                  : "com.alibaba:fastjson:${versions.fastjson}",
        //jackson依赖
        'jackson-dataformat-xml'    : "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${versions.jackson}",
        'com.fasterxml.jackson.core': "com.fasterxml.jackson.core:jackson-databind:${versions.jackson}",
        'jackson-annotations'       : "com.fasterxml.jackson.core:jackson-annotations:${versions.jackson}"
]
```

- `build.gradle`配置文件中添加依赖

![image-20231020190918836](assets/image-20231020190918836.png)

<img src="assets/image-20231020190949073.png" alt="image-20231020190949073" style="zoom:67%;" />

## 修改WebMvc配置

- 修改`com.zhouyf.config.WebConfig`，添加 XML 消息转换器

```java
package com.zhouyf.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
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
        //这确保了Spring会使用这个转换器来处理JSON消息。
        converters.add(fastJsonHttpMessageConverter);

        //创建一个MappingJackson2XmlHttpMessageConverter实例
        // 添加 XML 消息转换器
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        converters.add(xmlConverter);
    }
}
```

- 创建一个`com.zhouyf.vo.Person`类

```java
package com.zhouyf.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class Person {
    @XmlElement
    private int age;
    @XmlElement
    private String name;
}
```

- 控制器`com.zhouyf.action.PersonAction`

在`PersonAction`中，我们指定了`produces = MediaType.APPLICATION_XML_VALUE`，表明此方法将产生XML响应，因此将使用Jackson的XML转换器。

```java
package com.zhouyf.action;

import com.zhouyf.vo.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person/*", produces = MediaType.APPLICATION_XML_VALUE)
public class PersonAction {
    @RequestMapping("info")
    public Person echo(Person person){
        person.setAge(person.getAge() + 1);
        person.setName("【INFO】"+person.getName());
        return person;
    }
}
```

- 修改`com.zhouyf.action.MessageAction`控制器

在`MessageAction`中，我们指定了`produces = MediaType.APPLICATION_JSON_VALUE`，这意味着这个方法将产生JSON响应，因此将使用FastJSON转换器。

```java
package com.zhouyf.action;

import com.zhouyf.common.action.abs.AbstractBaseAction;
import com.zhouyf.vo.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//直接进行Rest架构进行处理，省略了@ResponseBody注解
@RequestMapping("/message/*")
public class MessageAction extends AbstractBaseAction {

    @RequestMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message echo(Message message){
        message.setTitle("【ECHO】"+message.getTitle());
        message.setContent("【ECHO】"+message.getContent());
        return message;
    }
}
```

## 启动测试

![image-20231020200552029](assets/image-20231020200552029.png)

![image-20231020200618820](assets/image-20231020200618820.png)

可以看到`/person/info`返回了XML数据而`/message/echo`返回了JSON数据