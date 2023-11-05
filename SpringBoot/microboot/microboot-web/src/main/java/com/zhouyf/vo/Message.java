package com.zhouyf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Message {
    private String title;
    @JsonFormat(pattern = "yyyy年MM月")
    private Date pubdate;
    private String content;
}
