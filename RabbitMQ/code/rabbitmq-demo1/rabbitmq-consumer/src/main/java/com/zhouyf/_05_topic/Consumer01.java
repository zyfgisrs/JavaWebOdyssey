package com.zhouyf._05_topic;

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

        channel.exchangeDeclare("05-topic", BuiltinExchangeType.TOPIC);
        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, "05-topic", "*.err");


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
