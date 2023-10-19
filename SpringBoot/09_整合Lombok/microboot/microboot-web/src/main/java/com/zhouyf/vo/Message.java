package com.zhouyf.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Message {
    private String title;
    private Date pubdate;
    private String content;
}
