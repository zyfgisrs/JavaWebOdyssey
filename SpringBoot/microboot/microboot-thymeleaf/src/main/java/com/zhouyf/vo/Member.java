package com.zhouyf.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class Member {
    private String name;

    private String mid;

    private Integer age;

    private Double salary;

    private Date birthday;
}
