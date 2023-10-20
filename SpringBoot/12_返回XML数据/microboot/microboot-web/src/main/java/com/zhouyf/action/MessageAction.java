package com.zhouyf.action;

import com.zhouyf.common.action.abs.AbstractBaseAction;
import com.zhouyf.vo.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//直接进行Rest架构进行处理，省略了@ResponseBody注解
@RequestMapping("/message/*")
public class MessageAction extends AbstractBaseAction {

    @RequestMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message echo(Message message){
        message.setTitle("【ECHO】"+message.getTitle());
        message.setContent("【ECHO】"+message.getContent());
        return message;
    }
}
