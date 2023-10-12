package com.zhouyf.vo;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@NoArgsConstructor
public class Message {
    @NonNull
    private String title;
    private Date pubdate;

    private String content;
}
