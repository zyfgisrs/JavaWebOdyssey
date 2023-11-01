# 返回JSON数据

- `com.zhouyf.vo.Message`

```java
package com.zhouyf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private String title;
    @JsonFormat(pattern = "yyyy年MM月")
    private Date pubdate;
    private String content;
}
```

`com.zhouyf.action.MessageAction`

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
    public Message echo(Message message) {
        message.setTitle("【ECHO】" + message.getTitle());
        message.setContent("【ECHO】" + message.getContent());
        return message;
    }
}
```

- 访问测试

![image-20231101191201639](assets/image-20231101191201639.png)

# 返回XML数据

## 添加依赖库

- 需要的依赖：

```
implementation group: 'com.fasterxml.jackson.dataformat', name: 'jackson-dataformat-xml', version: '2.12.2'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.12.2'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.12.2'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.12.2'
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
    'jackson-core'             : "com.fasterxml.jackson.core:jackson-core:${versions.jackson}",
]
```

- `build.gradle`配置文件中添加依赖

```groovy
project('microboot-web') { // 子模块
    dependencies { // 配置子模块依赖
        //jackson相关依赖
        compile(libraries.'jackson-dataformat-xml')
        compile(libraries.'jackson-databind')
        compile(libraries.'jackson-annotations')
        compile(libraries.'jackson-core')
    }
}
```

<img src="assets/image-20231020190949073.png" alt="image-20231020190949073" style="zoom:67%;" />

## 修改WebMvc配置

- 修改`com.zhouyf.config.WebConfig`，添加 XML 消息转换器

```java
package com.zhouyf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        //创建一个MappingJackson2XmlHttpMessageConverter实例
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        //添加 XML 消息转换器
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

在`MessageAction`中，我们指定了`produces = MediaType.APPLICATION_JSON_VALUE`，这意味着这个方法将产生JSON响应。

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

# Jackson相关注解

**@JsonProperty** - 用于指示JSON属性的名称。对于那些在Java字段和JSON属性之间名称不同的情况特别有用。

```java
@Data
public class Message {
    @JsonProperty("标题")
    private String title;
    @JsonFormat(pattern = "yyyy年MM月")
    private Date pubdate;
    private String content;
}
```

![image-20231101191635636](assets/image-20231101191635636.png)

**@JsonIgnore** - 表示在序列化或反序列化过程中该属性应被忽略。

```java
public class Message {
    @JsonProperty("标题")
    private String title;
    @JsonFormat(pattern = "yyyy年MM月")
    private Date pubdate;
    @JsonIgnore
    private String content;
}
```

![image-20231101191806294](assets/image-20231101191806294.png)

**@JsonFormat** - 定制日期、时间和数字的格式。

**@JsonUnwrapped** - 允许嵌套的属性在JSON中展平

**@JsonInclude** - 用于控制属性的包含规则。