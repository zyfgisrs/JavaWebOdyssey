package com.zhouyf.action;

import com.zhouyf.vo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept/*")
public class DeptAction {

    @Autowired
    private Dept dept;

    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dept showDept(){
        return dept;
    }
}
