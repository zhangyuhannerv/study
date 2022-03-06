package com.study.spring5_demo3.test;

import com.study.spring5_demo3.config.SpringConfig;
import com.study.spring5_demo3.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
        userService.printProp();
    }

    // 完全注解开发，不用xml配置文件，而是用使用@Configuration的配置类
    @Test
    public void testConfigClass() {
        // 这里创建context对象和上面不同，new()是不一样的，要注意
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
        userService.printProp();
    }
}
