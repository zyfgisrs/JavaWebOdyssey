package com.zhouyf.consumer._02_worker;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer1 {

    @RabbitListener(queuesToDeclare = @Queue("boot_worker"))
    public void consumer(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("【消费者1】-消息内容" + msg);
        channel.basicAck(deliveryTag, true);
    }
}
