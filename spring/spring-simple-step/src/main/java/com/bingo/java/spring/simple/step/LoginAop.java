package com.bingo.java.spring.simple.step;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: LoginAop
 * @projectName java-hub
 */
@Component
@Aspect
public class LoginAop {
    @Pointcut("execution(* com.bingo.java.spring.simple.step.UserService.login(*))")
    public void pointCut(){

    }
    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("pointCut()")
    public void beforeExcution(JoinPoint joinPoint){

        System.out.println("aop 前置通知");

    }

    /**
     * 后置返回
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "pointCut()")
    public void afterReturning(Object result) throws Throwable{
        System.out.println("aop 后置返回 "+result);
    }

}
