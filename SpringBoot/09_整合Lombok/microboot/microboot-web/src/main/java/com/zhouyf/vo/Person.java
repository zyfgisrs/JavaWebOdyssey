package com.zhouyf.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Person {
    private String name;

    private int age;
}
