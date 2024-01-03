package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Department;
import com.zhouyf.zhouyfbatis.model.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("""
            SELECT id, name, description from department where id = #{id};
                    """)

    @Results(id = "departmentMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "employees", column = "id",
                    many = @Many(select = "selectEmployeeByDepartmentId"))

    })
    Department selectDepartmentById(int id);


    @Select("""
            SELECT id, name, email, department_id, position, hire_date from employee
            where department_id = #{departmentId}
                """)
    List<Employee> selectEmployeeByDepartmentId(@Param("departmentId") int departmentId);
}
