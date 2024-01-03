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
