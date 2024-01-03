package com.zhouyf.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhouyf.domin.entity.UmsSysUser;
import com.zhouyf.mapper.UmsSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UmsSysUserDetailsService implements UserDetailsService {
    final UmsSysUserMapper userMapper;

    public UmsSysUserDetailsService(UmsSysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据用户名查询用户，如果没有查到用户会抛出异常
     *
     * @param username 用户名称
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByName=======>{}", username);
        UmsSysUser umsSysUser = userMapper.selectOne(new LambdaQueryWrapper<UmsSysUser>().eq(UmsSysUser::getUsername, username));
        log.info("umsSysUser=======>{}", umsSysUser);

        return umsSysUser;//UmsSysUser已经实现了UserDetails接口
    }
}
