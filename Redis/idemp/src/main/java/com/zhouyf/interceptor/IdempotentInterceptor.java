package com.zhouyf.interceptor;

import com.zhouyf.annotation.Idempontent;
import com.zhouyf.service.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;



@Slf4j
@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private ITokenService iTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idempontent idempontent = method.getAnnotation(Idempontent.class);
        //如果在实际开发中，发现token不正确或者是code有问题，应该返回错误信息
        if(idempontent != null){//方法上已经标记了所需的注解
            return check(request);
        }
        return true;
    }

    private boolean check(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            token = request.getParameter("token");
            if(token == null || token.isEmpty()){
                log.error("【幂等性检测】检测失败，没有传递token数据。");
            }
        }

        String code = request.getParameter("code");
        if(token != null && code != null){
            return this.iTokenService.checkToken(code, token);
        }
        return false;
    }
}
