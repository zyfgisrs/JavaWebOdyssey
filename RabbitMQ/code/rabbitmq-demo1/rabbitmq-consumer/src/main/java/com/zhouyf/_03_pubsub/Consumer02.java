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
        //设置队列属性
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
