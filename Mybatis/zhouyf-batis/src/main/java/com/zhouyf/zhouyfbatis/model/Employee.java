package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;

    private String name;

    private String email;

    private int departmentId;

    private String position;

    private LocalDate hireDate;
}
