package com.zhouyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhouyf.domin.entity.UmsRole;
import com.zhouyf.domin.entity.UmsSysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UmsSysUserMapper extends BaseMapper<UmsSysUser> {

    @Select("""
            select id, username, nickname, email, phone, sex, password, status, deleted
            from ums_sys_user
            where username = #{username};
                  """)

    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "nickname", property = "nickname"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "deleted", property = "deleted"),
            @Result(property = "roles", column = "id", many =
            @Many(select = "selectRoleById"))
    })
    UmsSysUser selectUserByUsername(@Param("username") String username);


    @Select("""
            select r.id, r.role_label, r.role_name, r.status, r.deleted, r.remark
            from ums_sys_user_role ur left join ums_role r on ur.role_id = r.id
            where ur.user_id = #{id};
                   """)

    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "role_label", property = "roleLabel"),
            @Result(column = "role_name", property = "roleName"),
            @Result(column = "status", property = "status"),
            @Result(column = "deleted", property = "deleted"),
            @Result(column = "remark", property = "remark"),
    })
    List<UmsRole> selectRoleById(int id);
}
