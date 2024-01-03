# Pub/Sub模式

RabbitMQ中的发布订阅模式（Publish/Subscribe），也称为“Pub/Sub”模式，是一种消息传递模式，允许我们将消息发送给多个消费者。在这个模式中，生产者（发布者）不会直接发送消息给特定的队列。相反，生产者发布消息到一个交换器（Exchange），然后交换器将消息路由到一个或多个绑定到该交换器的队列，最终由一个或多个消费者（订阅者）接收这些消息。这种模式对于实现消息的广播传播非常有效。

## 交换机

接收生产者发送的消息，并根据特定规则将其路由到一个或多个队列。在发布订阅模式中，通常使用的是“扇出”（fan-out）类型的交换器。

## PubSub工作原理

1. **创建交换器**：首先，创建一个类型为“扇出”的交换器。这种类型的交换器会将接收到的所有消息广播到所有绑定到它的队列。
2. **绑定队列**：创建队列，并将它们绑定到交换器上。在发布订阅模式中，可以有多个队列绑定到同一个交换器。
3. **发布消息**：生产者发送消息到交换器，交换器将消息路由到所有绑定的队列。
4. **接收消息**：每个队列的消费者接收消息并进行处理。每个绑定到交换器的队列都会接收到消息的副本。

## Pub/Sub模式范例

### 生产者

在发布订阅模式下，将消息发送到交换机

```java
package com.zhouyf._03_pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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

        /**
         * 第一个参数：交换机的名字
         * 第二个参数：交换机的类型
         */
        channel.exchangeDeclare("03-pubsub", BuiltinExchangeType.FANOUT);
        //循环发送消息
        for (int i = 0; i < 20; i ++){
            String msg = "hello, rabbitmq : " + i;
            channel.basicPublish("03-pubsub", "", null, msg.getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
```

### 消费者

消费者1从交换机接收消息

```java
package com.zhouyf._03_pubsub;

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
        //设置队列属性
        channel.exchangeDeclare("03-pubsub", BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        System.out.println(queue);
        channel.queueBind(queue, "03-pubsub", "");

        channel.basicConsume(queue, true, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                System.out.println("消息者1的消费内容为 : " + new String(delivery.getBody()));
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

消费者2从交换机中接收消息

```java
package com.zhouyf._03_pubsub;

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
		
        channel.exchangeDeclare("03-pubsub", BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "03-pubsub", "");

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

### 测试

消费者1和消费者2都消费了消息。

总结：

- 生产者发送消息到扇出交换器，不关心谁会接收消息。
- 每个消费者创建自己的队列并绑定到同一个交换器。
- 当生产者发送消息到交换器时，所有绑定的队列都会收到消息

## 生产者消费者与交换机的联系

生产者的`Channel`是执行操作的通道，而`Exchange`是消息路由的中心。生产者通过`Channel`将消息发送到`Exchange`，然后`Exchange`根据其配置将消息路由到一个或多个队列。

交换机作为消息路由的中心环节，负责根据特定的规则将消息路由到一个或多个`Queue`（队列）。消费者则是直接与队列相连（消费者中需要将队列绑定到交换机），从队列中获取消息进行处理。队列是RabbitMQ中用于存储消息的组件。每个消息都会被路由到一个或多个队列，并在那里等待被消费者处理。

## 默认交换机

当生产者发送消息时，如果没有指定交换机，就会使用这个默认交换机。在这种情况下，生产者需要在发送消息时指定路由键，这个路由键通常是目标队列的名称。如在工作者模式中，在生产者发送消息时，需要指定路由键。
