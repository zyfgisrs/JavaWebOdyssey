package com.zhouyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhouyf.domin.entity.UmsMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    List<UmsMenu> selectMenuByRoleId(@Param(value = "roleSet") Set<Integer> roleSet);
}
