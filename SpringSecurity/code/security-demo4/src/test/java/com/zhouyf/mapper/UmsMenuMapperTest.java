package com.zhouyf.mapper;

import com.zhouyf.domin.entity.UmsMenu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UmsMenuMapperTest {

    @Autowired
    private UmsMenuMapper umsMenuMapper;

    @Test
    void test(){
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        List<UmsMenu> menus = umsMenuMapper.selectMenuByRoleId(set);
        System.out.println(menus);
    }
}