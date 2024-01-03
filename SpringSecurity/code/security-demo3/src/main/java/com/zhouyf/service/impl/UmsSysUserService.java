package com.zhouyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.domin.entity.UmsSysUser;
import com.zhouyf.mapper.UmsSysUserMapper;
import com.zhouyf.service.IUmsSysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UmsSysUserService extends ServiceImpl<UmsSysUserMapper, UmsSysUser> implements IUmsSysUser {
    final AuthenticationManager authenticationManager;

    public UmsSysUserService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public String login(LoginParams loginParams) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UmsSysUser umsSysUser = (UmsSysUser) authenticate.getPrincipal();
        log.info("登录后的用户========>{}", umsSysUser);
        return UUID.randomUUID().toString().replace("-", "");
    }
}
