package com.study.spring5_demo2.test;

import com.study.spring5_demo2.bean.Orders;
import com.study.spring5_demo2.collectionType.Book;
import com.study.spring5_demo2.collectionType.Course;
import com.study.spring5_demo2.collectionType.Stu;
import com.study.spring5_demo2.factoryBean.MyBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/2/28
 * @Version 1.0
 */
public class TestDemo {
    /**
     * 测试注入集合
     */
    @Test
    public void testCollection() {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean4.xml");
        Stu stu = app.getBean("stu", Stu.class);
        System.out.println(stu);
    }

    /**
     * 测试注入抽取出的集合
     */
    @Test
    public void testCollection1() {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean5.xml");
        Book book = app.getBean("book", Book.class);
        System.out.println(book);
    }

    /**
     * 测试factory bean。与普通bean的最大区别是(返回的类型可能和xml里配置的类型不一样)
     */
    @Test
    public void factoryBean() {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean6.xml");
        // 当重写了方法之后，就报错了
        // MyBean myBean = app.getBean("myBean", MyBean.class);
        // System.out.println(myBean);

        Course myBean = app.getBean("myBean", Course.class);
        System.out.println(myBean);
    }

    /**
     * 测试单实例（默认)和多实例
     */
    @Test
    public void testSingleAndMultiIns() {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean5.xml");
        Book book = app.getBean("book", Book.class);
        System.out.println(book);
        Book book1 = app.getBean("book", Book.class);
        System.out.println(book1);

        // 发现地址相同
        // 在bean标签里设置scope=prototype后，发现地址不同
    }

    /**
     * 测试bean的生命周期
     */
    @Test
    public void testLifeCycle() {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean7.xml");
        Orders orders = app.getBean("orders", Orders.class);
        System.out.println("第四步，获取到了创建的bean实例");
        System.out.println(orders);

        // 需要手动让bean实例(注意：父类没有close()接口)
        ((ClassPathXmlApplicationContext) app).close();
    }
}
