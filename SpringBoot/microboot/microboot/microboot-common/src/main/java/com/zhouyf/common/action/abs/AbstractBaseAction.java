package com.zhouyf.common.action.abs;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractBaseAction { //定义一个公共的Action类
    //将字符串转换为日期，考虑到多线程环境下的并发问题，一定要使用LocalDate
    private static final DateTimeFormatter LOCAL_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(java.util.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                LocalDate localDate = LocalDate.parse(text, LOCAL_DATE_FORMAT);
                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                super.setValue(java.util.Date.from(instant));
            }
        });
    }
}
