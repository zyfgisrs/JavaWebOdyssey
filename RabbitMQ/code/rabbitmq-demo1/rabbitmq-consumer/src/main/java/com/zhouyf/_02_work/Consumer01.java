package com.zhouyf._02_work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
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
        channel.queueDeclare("02-work", false, false, false, null);
        channel.basicQos(1);

        channel.basicConsume("02-work", false, new DeliverCallback() {
            /**
             * 当消息从mq中取出来了会调用这个方法，消费者消费消息就在这个handle中去进行处理
             * @param s
             * @param delivery
             * @throws IOException
             */
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消息的内容为" + new String(delivery.getBody()));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
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
