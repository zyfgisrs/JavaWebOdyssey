package com.zhouyf.consumer._01_hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queuesToDeclare = @Queue("boot-hello"))
    public void consumer(String msg){
        System.out.println("消息内容为：" + msg);
    }
}
