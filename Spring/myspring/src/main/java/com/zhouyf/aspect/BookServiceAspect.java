package com.zhouyf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class BookServiceAspect {
    @Pointcut("execution(* com.zhouyf.service..*.*(..))")
    public void bookServiceLayer(){

    }

    @Before("bookServiceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("【@Before】logBefore() is running!");
        System.out.println("【@Before】Intercepted : " + joinPoint.getSignature().getName());
    }

    // 通知（环绕通知）
    @Around("bookServiceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("【@Around】Around before is running!");
        System.out.println("【@Around】Method : " + joinPoint.getSignature().getName());
        System.out.println("【@Around】Arguments : " + Arrays.toString(joinPoint.getArgs()));
        Object returnValue = joinPoint.proceed();  // 继续执行连接点方法
        System.out.println("【@Around】" + returnValue);
        System.out.println("【@Around】Around after is running!");
        return returnValue;
    }

    @AfterReturning(pointcut = "bookServiceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("【@AfterReturning】logAfterReturning() is running!");
        System.out.println("【@AfterReturning】Method returned value is : " + result);
    }

    @After("bookServiceLayer()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("【@After】logAfter is running!");
    }
}
