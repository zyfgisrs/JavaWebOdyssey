package com.zhouyf.domin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@TableName("ums_sys_user")
@Data
public class UmsSysUser implements UserDetails, Serializable {
    @TableId
    private int id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private int sex;
    private String password;
    private int status;
    @TableLogic
    private int deleted;
    private List<UmsRole> roles = new ArrayList<>(); //角色
    private List<String> menus = new ArrayList<>(); //权限


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将权限告知spring

        Set<SimpleGrantedAuthority> grantedAuthorities = null;
        if (menus != null && !menus.isEmpty()) {
            grantedAuthorities = menus.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isEnabled() {
        return status == 0;
    }
}
