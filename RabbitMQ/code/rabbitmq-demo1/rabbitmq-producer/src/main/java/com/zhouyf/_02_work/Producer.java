package com.zhouyf._02_work;

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
        //设置队列属性
        channel.queueDeclare("02-work", false, false, false, null);
        //循环发送消息
        for (int i = 0; i < 20; i ++){
            String msg = "hello, rabbitmq : " + i;
            channel.basicPublish("", "02-work", null, msg.getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
