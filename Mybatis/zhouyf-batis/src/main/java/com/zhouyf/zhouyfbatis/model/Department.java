package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int id;

    private String name;

    private String description;

    private List<Employee> employees;
}
