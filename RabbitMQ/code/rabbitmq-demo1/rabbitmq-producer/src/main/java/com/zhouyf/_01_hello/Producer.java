package com.zhouyf._01_hello;

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
        //第一个参数：队列名称
        //第二个参数：队列是否需要持久化
        //第三个参数：是否排他性(一般指定为false)
        //第四个参数：是否自动删除，如果没有消费者连接那就自动删除
        //第五个参数：是否需要设置一些额外参数
        channel.queueDeclare("01-hello", false, false, false, null);
        //发送消息
        //第三个参数：消息的属性，可以配置是否持久化
        channel.basicPublish("", "01-hello", null, "hello, rabbitmq1".getBytes());
        //关闭资源
        channel.close();
        connection.close();
    }
}
