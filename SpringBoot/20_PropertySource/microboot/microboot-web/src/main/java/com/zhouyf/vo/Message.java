package com.zhouyf.vo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private String title;
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubdate;

    private String content;
}
