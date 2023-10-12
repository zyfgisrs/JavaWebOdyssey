package com.zhouyf.action;

import com.zhouyf.service.ImessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/*")
public class MessageAction {

    @Autowired
    private ImessageService messageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAction.class);

    @RequestMapping("echo")
    public String echo(String msg){
        LOGGER.info("接受到的msg请求参数:{}",msg);
        return this.messageService.echo(msg);
    }
}
