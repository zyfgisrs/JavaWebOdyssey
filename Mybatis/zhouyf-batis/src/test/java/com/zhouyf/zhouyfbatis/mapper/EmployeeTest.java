package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    void select(){
        Department department = departmentMapper.selectDepartmentById(1);
        System.out.println(department);
    }
}
