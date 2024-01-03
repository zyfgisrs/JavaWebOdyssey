package com.zhouyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.domin.entity.UmsSysUser;
import com.zhouyf.mapper.UmsSysUserMapper;
import com.zhouyf.service.IUmsSysUserService;
import com.zhouyf.web.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
public class UmsSysUserServiceImpl extends ServiceImpl<UmsSysUserMapper, UmsSysUser> implements IUmsSysUserService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public UmsSysUserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 间接调用UmsSysDetailsService中的loadUserByUsername方法
     *
     * @param loginParams
     * @return
     */
    public String login(LoginParams loginParams) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("用户名和密码错误");
            return "";
        }

        UmsSysUser umsSysUser = (UmsSysUser)auth.getPrincipal();
        if(umsSysUser == null){
            log.error("用户名和密码错误");
            return "";
        }
        HashMap<String, Object> info = new HashMap<>();
        info.put("id", umsSysUser.getId());
        info.put("username", umsSysUser.getUsername());
        info.put("perms", umsSysUser.getMenus());
        return jwtUtils.createToken(info);
    }
}
