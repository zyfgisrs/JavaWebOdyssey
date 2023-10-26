package com.zhouyf.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Student {
    @Excel(name = "学生姓名", orderNum = "0", width = 30)
    private String name;

    @Excel(name = "学生年龄", orderNum = "1", width = 10)
    private int age;
    @Excel(name = "学生ID", orderNum = "2", width = 10)
    private String id;
}
