package com.zhouyf.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/source/*")
public class SourceAction {
    @Value("${source.mysql}")
    private String mysql;
    @Value("${source.redis}")
    private String redis;
    @Value("${source.messages}")
    private List<String> messages;
    @Value("#{${source.info}}")
    private Map<String, Object> infos;


    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showSource(){
        HashMap<String, Object> info = new HashMap<>();
        info.put("mysql", mysql);
        info.put("redis", redis);
        info.put("message", messages);
        info.put("infos", infos);
        return info;
    }
}
