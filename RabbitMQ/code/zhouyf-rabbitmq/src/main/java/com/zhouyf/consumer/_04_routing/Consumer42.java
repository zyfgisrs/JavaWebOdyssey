package com.zhouyf.consumer._04_routing;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer42 {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "boot-routing", type = "direct"),
            value = @Queue,
            key = {"error"}
    ))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("【消费者2】-消息内容 : " + msg);
        channel.basicAck(deliveryTag, true);
    }
}
