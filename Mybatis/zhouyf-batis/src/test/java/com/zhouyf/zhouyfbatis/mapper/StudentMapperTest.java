package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void testSelect(){
        Student student = studentMapper.selectStudentWithCourses(1);
        System.out.println(student);
    }
}
