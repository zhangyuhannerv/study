package com.study.spring5_demo1.testDemo;

import com.study.spring5_demo1.Book;
import com.study.spring5_demo1.Orders;
import com.study.spring5_demo1.User;
import com.study.spring5_demo1.bean.Emp;
import com.study.spring5_demo1.service.UserService;
import com.study.spring5_demo1.service.UserServiceImpl;
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
        // ApplicationContext是BeanFactory接口的子接口，它比BeanFactory提供更多更强大的功能
        // 它一般由开发人员使用
        // Application在加载配置文件时就会把配置文件中的对象进行创建
        // Application有一些实现类
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");

        // BeanFactory是spring内部接口，一般不在开发中使用
        // BeanFactory在加载配置文件时，不会去创建对象
        // 在某处获取对象时，才会去新建对象
        // BeanFactory context =
        //         new ClassPathXmlApplicationContext("bean1.xml");
        // 2.获取配置创建的对象
        User user = context.getBean("user", User.class);
        // 3.调用方法
        System.out.println(user);
        user.add();
    }


    /**
     * 测试spring使用set方法给对象注入属性
     */
    @Test
    public void testBook() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");

        Book book = context.getBean("book", Book.class);
        System.out.println(book);
    }

    /**
     * 测试spring使用set方法给对象注入属性(p名称空间注入)
     */
    @Test
    public void testBook1() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");

        Book book1 = context.getBean("book1", Book.class);
        System.out.println(book1);
    }

    /**
     * 测试spring使用constructor方法给对象注入属性
     */
    @Test
    public void testOrders() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");

        Orders orders = context.getBean("orders", Orders.class);
        System.out.println(orders);
    }

    /**
     * 测试注入的属性是引用对象
     */
    @Test
    public void testSetRef() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean2.xml");
        UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);
        userService.add();
    }


    /**
     * 测试注入属性对象的内部写法
     */
    @Test
    public void testSetInnerRef() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean3.xml");
        Emp emp = applicationContext.getBean("emp", Emp.class);
        System.out.println(emp);

        Emp emp1 = applicationContext.getBean("emp1", Emp.class);
        System.out.println(emp1);

        Emp emp2 = applicationContext.getBean("emp2", Emp.class);
        System.out.println(emp2);
    }

}