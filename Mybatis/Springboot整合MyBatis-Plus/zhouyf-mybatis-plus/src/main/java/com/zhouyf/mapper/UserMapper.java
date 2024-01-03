package com.zhouyf.mapper;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhouyf.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("""
            UPDATE user set balance = balance - #{amount} ${ew.customSqlSegment}
              """)
    int updateBalanceById(@Param("ew") LambdaUpdateWrapper<User> wrapper, int amount);
}
