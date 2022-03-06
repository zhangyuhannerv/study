package com.study.spring5_aop.test;

import com.study.spring5_aop.ano.User;
import com.study.spring5_aop.config.SpringConfig;
import com.study.spring5_aop.xml.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestAop
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */
public class TestAop {
    @Test
    public void testAopAno() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_aop/bean.xml");
        User user = app.getBean("user", User.class);
        user.add();
    }

    @Test
    public void testAopXml() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_aop/bean2.xml");
        Book book = app.getBean("book", Book.class);
        book.buy();
    }

    @Test
    public void testAllAnoAop(){
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = app.getBean("user", User.class);
        user.add();
    }
}
