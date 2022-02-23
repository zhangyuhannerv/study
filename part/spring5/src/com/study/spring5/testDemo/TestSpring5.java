package com.study.spring5.testDemo;

import com.study.spring5.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestSpring5
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/2/23
 * @Version 1.0
 */
public class TestSpring5 {

    /**
     * 测试通过spring创建对象
     */
    @Test
    public void testAdd() {
        // 1.加载spring的配置文件
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");
        // 2.获取配置创建的对象
        User user = context.getBean("user", User.class);
        // 3.调用方法
        System.out.println(user);
        user.add();
    }
}
