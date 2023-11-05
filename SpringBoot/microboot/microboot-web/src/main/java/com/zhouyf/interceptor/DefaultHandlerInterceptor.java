package com.zhouyf.interceptor;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            System.out.println("action对象" + handlerMethod.getBean());
            System.out.println("action类型" + handlerMethod.getBeanType());
            System.out.println("action方法" + handlerMethod.getMethod());
        }
        return true;
    }
}
