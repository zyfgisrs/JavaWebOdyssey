# SpringBoot整合RabbitMQ

## 依赖配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit-test</artifactId>
    <scope>test</scope>
</dependency>
```

配置文件

```yml
spring:
  rabbitmq:
    host: localhost
    listener:
      simple:
        acknowledge-mode: manual
        prefetch: 1
```

## 控制器

使用控制器方法来接收请求并发送消息

```java
package com.zhouyf.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg")
    public String sendMsg(String msg) {
        rabbitTemplate.convertAndSend("", "boot-hello", msg);
        return "发送成功";
    }


    @RequestMapping("/worker")
    public String sendWorkerMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("", "boot_worker", msg + i);
        }
        return "发送成功";
    }

    @RequestMapping("/pubsub")
    public String sendPubSubMsg(String msg) {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("boot-pubsub", "", msg + i);
        }
        return "pubsub发送成功";
    }


    @RequestMapping("/routing")
    public String sendRoutingMsg(String msg, String key) {
        rabbitTemplate.convertAndSend("boot-routing", key, msg);
        return "routing发送成功";
    }


    @RequestMapping("/topic")
    public String sendTopicMsg(String msg, String key) {
        rabbitTemplate.convertAndSend("boot-topic", key, msg);
        return "topic发送成功";
    }
}
```

## Hello

```java
package com.zhouyf.consumer._01_hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queuesToDeclare = @Queue("boot-hello"))
    public void consumer(String msg){
        System.out.println("消息内容为：" + msg);
    }
}
```

## Worker队列模式

```java
package com.zhouyf.consumer._02_worker;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer1 {

    @RabbitListener(queuesToDeclare = @Queue("boot_worker"))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("【消费者1】-消息内容" + msg);
        channel.basicAck(deliveryTag, true);
    }
}
```

```java
package com.zhouyf.consumer._02_worker;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer2 {

    @RabbitListener(queuesToDeclare = @Queue("boot_worker"))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("【消费者2】-消息内容" + msg);
        channel.basicAck(deliveryTag, true);
    }
}
```

## Pub/Sub模式

```java
package com.zhouyf.consumer._03_pubsub;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer31 {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "boot-pubsub", type = "fanout"),
            value = @Queue))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("【消费者1】-消息内容" + msg);
        channel.basicAck(deliveryTag, true);
    }
}
```

## Routing模式

```java
package com.zhouyf.consumer._04_routing;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer41 {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "boot-routing", type = "direct"),
            value = @Queue,
            key = {"info", "error", "warning"}
    ))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("【消费者1】-消息内容 : " + msg);
        channel.basicAck(deliveryTag, true);
    }
}
```

## Topic模式

```java
package com.zhouyf.consumer._05_topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer51 {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "boot-topic", type = "topic"),
            value = @Queue,
            key = {"app1.*"}
    ))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("【消费者1】-消息内容 : " + msg);
        channel.basicAck(deliveryTag, true);
    }
}
```

