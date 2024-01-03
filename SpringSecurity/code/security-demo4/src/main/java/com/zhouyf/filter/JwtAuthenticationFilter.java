package com.zhouyf.filter;

import com.zhouyf.domin.entity.UmsSysUser;
import com.zhouyf.web.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 获取请求中的请求头，获取token字段，判断是否可以获取用户信息
 * <p>
 * 获取到用户信息后，需要将用户的信息告知spring，spring会判断接口是否具有该权限
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("authorization");
        //没有token直接放行
        if (token == null) {
            doFilter(request, response, filterChain);
            return;
        }
        //如果有token

        Claims info = jwtUtils.parseTokens(token);
        log.info("info==============>{}", info);
        //将信息放到UmsSysUser中
        int id = info.get("id", Integer.class);
        String username = info.get("username", String.class);
        List perms = info.get("perms", List.class);
        UmsSysUser umsSysUser = new UmsSysUser();
        umsSysUser.setId(id);
        umsSysUser.setUsername(username);
        umsSysUser.setMenus(perms);
        log.info("umsSysUser==============>{}", umsSysUser);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(umsSysUser, null, umsSysUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        doFilter(request, response, filterChain);
    }
}
