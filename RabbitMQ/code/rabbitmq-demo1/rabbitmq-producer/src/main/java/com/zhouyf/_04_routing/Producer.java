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

        //当生产者要发送消息时，它通过channel发送到指定的交换机
        /**
         * 第一个参数：交换机的名字
         * 第二个参数：交换机的类型
         */
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
