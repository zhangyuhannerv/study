package com.study.spring5_aop.ano;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserProxy
 * @Description 增强类
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */

@Component
// 表示生成代理对象
@Aspect
@Order(3)
public class UserProxy {
    // 相同切入点的抽取
    @Pointcut("execution(* com.study.spring5_aop.ano.User.add(..))")
    public void pointDemo() {

    }

    // 前置通知
    // @Before表示前置通知
    // @Before("execution(* com.study.spring5_aop.ano.User.add(..))")
    @Before("pointDemo()")
    public void before() {
        System.out.println("User before...");
    }

    // 后置通知(返回通知，执行顺寻在最终通知的后面)
    // @AfterReturning("execution(* com.study.spring5_aop.ano.User.add(..))")
    @AfterReturning("pointDemo()")
    public void afterReturning() {
        System.out.println("afterReturning...");
    }

    // 异常通知
    // @AfterThrowing("execution(* com.study.spring5_aop.ano.User.add(..))")
    @AfterThrowing("pointDemo()")
    public void afterThrowing() {
        System.out.println("afterThrowing...");
    }

    // 最终通知
    // @After("execution(* com.study.spring5_aop.ano.User.add(..))")
    @After("pointDemo()")
    public void after() {
        System.out.println("after...");
    }

    // 环绕通知
    // @Around("execution(* com.study.spring5_aop.ano.User.add(..))")
    @Around("pointDemo()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前...");

        // 被增强的方法执行
        proceedingJoinPoint.proceed();

        System.out.println("环绕之后");
    }
}
