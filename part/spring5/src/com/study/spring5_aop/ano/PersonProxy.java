package com.study.spring5_aop.ano;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName PersonProxy
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */
@Component
@Aspect
// 数字值越小，优先级越高。从0开始。
// 默认优先级是一个很大的数，执行顺序很低
@Order(1)
public class PersonProxy {

    @Before("execution(* com.study.spring5_aop.ano.User.add(..))")
    public void before() {
        System.out.println("Person before");
    }
}
