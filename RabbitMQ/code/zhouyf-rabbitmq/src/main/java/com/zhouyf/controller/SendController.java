package com.zhouyf.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg")
    public String sendMsg(String msg) {
        rabbitTemplate.convertAndSend("", "boot-hello", msg);
        return "发送成功";
    }


    @RequestMapping("/worker")
    public String sendWorkerMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("", "boot_worker", msg + i);
        }
        return "发送成功";
    }

    @RequestMapping("/pubsub")
    public String sendPubSubMsg(String msg) {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("boot-pubsub", "", msg + i);
        }
        return "pubsub发送成功";
    }


    @RequestMapping("/routing")
    public String sendRoutingMsg(String msg, String key) {
        rabbitTemplate.convertAndSend("boot-routing", key, msg);
        return "routing发送成功";
    }


    @RequestMapping("/topic")
    public String sendTopicMsg(String msg, String key) {
        rabbitTemplate.convertAndSend("boot-topic", key, msg);
        return "topic发送成功";
    }
}
