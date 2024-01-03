package com.zhouyf.web;

import com.zhouyf.domin.entity.UmsMenu;
import com.zhouyf.domin.entity.UmsRole;
import com.zhouyf.domin.entity.UmsSysUser;
import com.zhouyf.mapper.UmsMenuMapper;
import com.zhouyf.mapper.UmsSysUserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UmsSysDetailsService implements UserDetailsService {
    @Autowired
    private  UmsSysUserMapper userMapper;

    @Autowired
    private  UmsMenuMapper menuMapper;
    /**
     * 根据用户名将权限查出来
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询用户的角色信息
        UmsSysUser umsSysUser = userMapper.selectUserByUsername(username);
        log.info("loadUserByUsername umsSysUser============>{}", umsSysUser);
        //2.查询用户的权限信息
        if(umsSysUser != null){
            List<String> userMenus = umsSysUser.getMenus();

            List<UmsRole> roles = umsSysUser.getRoles();

            Set<Integer> roleSet = new HashSet<>();
            for (UmsRole role : roles) {
                roleSet.add(role.getId());
            }

            List<UmsMenu> menus = menuMapper.selectMenuByRoleId(roleSet);
            for (UmsMenu menu : menus) {
                String perms = menu.getPerms();
                //添加用户权限
                if(perms != null){
                    userMenus.add(perms);
                }
                System.out.println(userMenus);
            }
        }
        return umsSysUser;
    }
}
