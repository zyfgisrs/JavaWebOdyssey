package com.zhouyf.action;

import com.zhouyf.vo.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source/*")
public class SourceAction {
    @Autowired
    private Source source;

    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Source showSource() {
        return this.source;
    }
}
