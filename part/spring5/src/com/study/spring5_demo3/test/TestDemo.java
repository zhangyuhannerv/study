package com.study.spring5_demo3.test;

import com.study.spring5_demo3.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/3
 * @Version 1.0
 */
public class TestDemo {
    @Test
    public void testService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
