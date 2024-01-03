package com.zhouyf._05_topic;

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
        channel.exchangeDeclare("05-topic", BuiltinExchangeType.TOPIC);

        Random random = new Random();
        List<String> info =
                List.of("app1.err", "app2.err", "app2.info", "app1.info", "app1.warning");
        //循环发送消息
        for (int i = 0; i < 30; i ++){
            int index = random.nextInt(5);
            String key = info.get(index);
            String msg = key + "-RabbitMQ : " + i;
            channel.basicPublish("05-topic", key, null, msg.getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
