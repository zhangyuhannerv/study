package com.study.spring5_demo2.test;

import com.study.spring5_demo2.collectionType.Book;
import com.study.spring5_demo2.collectionType.Stu;
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
}
