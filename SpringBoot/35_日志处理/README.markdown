# Loombok日志注解

Lombok提供了多种日志注解，这些注解可以自动生成日志对象，从而使我们能够非常简单地在代码中使用日志记录。

```java
package com.zhouyf.action;

import com.zhouyf.common.action.abs.AbstractBaseAction;
import com.zhouyf.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController//直接进行Rest架构进行处理，省略了@ResponseBody注解
@RequestMapping("/message/*")
@Slf4j
public class MessageAction extends AbstractBaseAction {

    @RequestMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message echo(Message message) {
        log.info("请求参数：{}", message);
        message.setTitle("【ECHO】" + message.getTitle());
        message.setContent("【ECHO】" + message.getContent());
        return message;
    }
}
```

在上述示例中，你可以看到我们在类上使用了`@Slf4j`注解。这将自动生成一个名为`log`的`SLF4J`日志对象。我们可以直接使用`log.info`、`log.error`等方法来进行日志记录。

- 访问测试

```
http://localhost:8080/message/echo?title=zhouyf&content=sdhjs&pubdate=2021-12-12
```

控制台打印日志

```
2023-11-01 19:43:15.968  INFO 7052 --- [nio-8080-exec-2] com.zhouyf.action.MessageAction  : 请求参数：Message(title=zhouyf, pubdate=Sun Dec 12 00:00:00 CST 2021, content=sdhjs)
```

<img src="assets/image-20231101194551855.png" alt="image-20231101194551855" style="zoom:50%;" />

SpringBoot中默认采用`SLF4J`和`Logback`日志组合，spring-boot-starter自动引入了`spring-boot-starter-logging`的依赖。