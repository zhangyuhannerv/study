package com.study.spring5_generic;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @ClassName User
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/13
 * @Version 1.0
 */
public class User {
    public static void main(String[] args) {
        User user = new User();
    }

    /**
     * 函数式风格创建对象，交给spring进行管理（自己new对象，然后交给ioc管理）
     */
    @Test
    public void testGenericApplicationContext() {
        GenericApplicationContext context = new GenericApplicationContext();

        // 调用context的方法进行对象的注册
        // refresh()清空context。
        context.refresh();

        // 注册一个对象，使用lambda或者方法引用
        // context.registerBean(User.class,()->new User());
        context.registerBean("user", User.class, User::new);// 指定bean名称
        // context.registerBean(User.class, User::new);// 不指定bean名称

        // 获取在spring中注册的对象,注意，注册的时候如果没有指定bean的名称，那么只能通过包的全路径来获取bean
        User user = (User) context.getBean("user");
        // User user = (User) context.getBean("com.study.spring5_generic.User");

        System.out.println(user);
    }
}
