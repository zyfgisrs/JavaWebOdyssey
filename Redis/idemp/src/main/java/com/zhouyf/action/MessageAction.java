package com.zhouyf.action;


import com.zhouyf.annotation.Idempontent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageAction {

    @RequestMapping("/echo")
    @Idempontent
    public Object echo(@RequestParam("msg") String msg) {
        return "【ECHO】" + msg;
    }
}
