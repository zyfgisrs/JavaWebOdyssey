package com.zhouyf.filter;

import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class MessageFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("/message/echo".equals(request.getRequestURI())){
            String title = request.getParameter("title");
            if (StringUtils.hasLength(title)){
                System.out.println("【MessageFilter】title参数内容为:" + title);
            }
        }

        chain.doFilter(request, response);
    }
}
