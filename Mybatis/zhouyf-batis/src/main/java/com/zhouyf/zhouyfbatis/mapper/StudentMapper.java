package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Course;
import com.zhouyf.zhouyfbatis.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("""
            SELECT student_id, name FROM students WHERE student_id = #{id}
                """)
    @Results(id = "studentMap", value = {
            @Result(id = true, property = "id", column = "student_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "courses", column = "student_id",
            many = @Many(select = "selectCoursesForStudent"))
    })
    Student selectStudentWithCourses(int id);


    @Select("""
            SELECT c.course_id, c.title FROM courses c
            JOIN students_courses sc ON c.course_id = sc.course_id
            WHERE sc.student_id = #{studentId}
                    """)
    List<Course> selectCoursesForStudent(int studentId);
}
