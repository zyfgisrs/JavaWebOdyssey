package com.zhouyf.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
@Data
@ConfigurationProperties(prefix = "dept")

public class Dept {
    private Long deptno;

    private String dname;

    private Company company;

    private List<Emp> emps;
}
