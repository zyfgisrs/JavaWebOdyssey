package com.zhouyf.vo;

import lombok.Data;


import java.util.List;
@Data
public class Dept {

    private Long deptno;
    private String dname;
    private Company company;
    private List<Emp> emps;
}
