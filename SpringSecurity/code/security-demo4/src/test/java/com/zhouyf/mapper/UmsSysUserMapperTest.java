package com.zhouyf.mapper;

import com.zhouyf.domin.entity.UmsRole;
import com.zhouyf.domin.entity.UmsSysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class UmsSysUserMapperTest {

    @Autowired
    private UmsSysUserMapper userMapper;

    @Test
    void test(){
        UmsSysUser umsSysUser = userMapper.selectUserByUsername("zhouyf");
        System.out.println(umsSysUser);
    }


    @Test
    void test1(){
        List<UmsRole> umsRoles = userMapper.selectRoleById(1);
        System.out.println(umsRoles.size());
    }
}