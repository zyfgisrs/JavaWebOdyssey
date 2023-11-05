package com.zhouyf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    @Before("execution(* com.zhouyf.service.impl.MessageServiceImpl.echo(..))")
    //前置通知
    public void beforeEchoMethod(JoinPoint joinPoint) {
        System.out.println("Before executing echo method: " + joinPoint.getSignature().getName());
    }
}
