package com.zhouyf._01_hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
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
        //第一个参数：队列名称
        //第二个参数：队列是否需要持久化
        //第三个参数：是否排他性
        //第四个参数：是否自动删除
        //第五个参数：是否需要设置一些额外参数
        channel.queueDeclare("01-hello", false, false, false, null);

        channel.basicConsume("01-hello", true, new DeliverCallback() {
            /**
             * 当消息从mq中取出来了会调用这个方法，消费者消费消息就在这个handle中去进行处理
             * @param s
             * @param delivery
             * @throws IOException
             */
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                System.out.println("消息的内容为" + new String(delivery.getBody()));
            }
        }, new CancelCallback() {
            /**
             * 当消息取消了会回调这个方法
             * @param s
             * @throws IOException
             */
            @Override
            public void handle(String s) throws IOException {
                System.out.println("111111");
            }
        });
    }
}
