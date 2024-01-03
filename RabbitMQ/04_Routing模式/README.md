# Routing模式

RabbitMQ中的Routing模式，也称为路由模式，是一种消息传递模式，它允许生产者将消息发送到一个或多个具体的队列，基于一定的路由规则。这个模式主要是通过直连类型的交换机（Direct Exchange）来实现的。在Routing模式下，消息不是广播到所有队列，而是根据消息的路由键（routing key）和队列的绑定键（binding key）来选择性地路由到特定的队列。

基于您提供的RabbitMQ路由（Routing）模式的代码，这里是相关的实现笔记。代码中包括一个生产者（`Producer`）和两个消费者（`Consumer01` 和 `Consumer02`）。

# 范例

## 生产者（Producer）
- **目的**：向指定的路由键发送不同类型的消息。
- **关键步骤**：
  1. 创建`ConnectionFactory`并设置RabbitMQ服务器地址（`localhost`）。
  2. 通过`ConnectionFactory`创建`Connection`。
  3. 通过`Connection`创建`Channel`。
  4. 声明一个直连类型的交换机（`04-routing`）。
  5. 随机生成不同类型的路由键（`info`, `error`, `warning`）并发送20条消息。
  6. 关闭`Channel`和`Connection`。

```java
package com.zhouyf._04_routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq ip地址
        connectionFactory.setHost("localhost");
        //创建Connection对象
        Connection connection = connectionFactory.newConnection();
        //创建chanel
        Channel channel = connection.createChannel();

        //当生产者要发送消息时，它通过channel发送到指定的交换
        channel.exchangeDeclare("04-routing", BuiltinExchangeType.DIRECT);

        Random random = new Random();
        List<String> info = List.of("info", "error", "warning");
        //循环发送消息
        for (int i = 0; i < 20; i ++){
            int index = random.nextInt(3);
            String key = info.get(index);
            String msg = key + "-RabbitMQ : " + i;
            channel.basicPublish("04-routing", key, null, msg.getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
```

## 消费者1（Consumer01）
- **目的**：接收特定路由键的消息（`info`, `error`, `warning`）。
- **关键步骤**：
  1. 创建`ConnectionFactory`，设置RabbitMQ服务器地址。
  2. 通过`ConnectionFactory`创建`Connection`。
  3. 通过`Connection`创建`Channel`。
  4. 声明同一个直连交换机（`04-routing`）。
  5. 创建一个队列并将其绑定到交换机上，绑定键为`info`, `error`, `warning`。
  6. 使用`basicConsume`方法从队列消费消息。

```java
package com.zhouyf._04_routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq ip地址
        connectionFactory.setHost("localhost");
        //创建Connection对象
        Connection connection = connectionFactory.newConnection();
        //创建chanel
        Channel channel = connection.createChannel();
        //声明一个交换机，类型为扇出，扇出类型的交换器会将接收到的消息发送到所有绑定的队列
        channel.exchangeDeclare("04-routing", BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        // 将声明的队列绑定到交换器。对于扇出交换器，路由键将被忽略
        channel.queueBind(queue, "04-routing", "info");
        channel.queueBind(queue, "04-routing", "error");
        channel.queueBind(queue, "04-routing", "warning");

        // 设置消费者订阅队列。这里的true表示使用自动确认模式
        channel.basicConsume(queue, true, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                System.out.println("消息者1的消费内容为 : " + new String(delivery.getBody()));
            }
        }, new CancelCallback() {
            // 当消费者被取消时（例如队列被删除），会调用此回调函数
            @Override
            public void handle(String s) throws IOException {
                System.out.println("111111");
            }
        });
    }
}
```

## 消费者2（Consumer02）
- **目的**：接收特定路由键的消息（仅`info`）。
- **关键步骤**：
  1. 与消费者1步骤类似，但仅将队列绑定到`info`路由键。

```java
package com.zhouyf._04_routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq ip地址
        connectionFactory.setHost("localhost");
        //创建Connection对象
        Connection connection = connectionFactory.newConnection();
        //创建chanel
        Channel channel = connection.createChannel();
        //设置队列属性
        channel.exchangeDeclare("04-routing", BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "04-routing", "info");

        channel.basicConsume(queue, true, new DeliverCallback() {

            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                System.out.println("消息者2的消费内容为 : " + new String(delivery.getBody()));
            }
        }, new CancelCallback() {

            @Override
            public void handle(String s) throws IOException {
                System.out.println("111111");
            }
        });
    }
}
```

## 路由模式的体现
- 生产者发送消息到交换机时指定路由键。
- 消费者通过绑定键（与路由键匹配）来接收特定类型的消息。
- 消费者1接收所有三种类型的消息，而消费者2仅接收`info`类型的消息。

## 使用场景
- 这种模式适用于当你需要根据消息类型将消息路由到不同的队列中，例如根据日志级别路由日志消息。
