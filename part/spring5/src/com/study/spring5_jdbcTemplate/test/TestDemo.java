package com.study.spring5_jdbcTemplate.test;

import com.study.spring5_jdbcTemplate.entity.Book;
import com.study.spring5_jdbcTemplate.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/7
 * @Version 1.0
 */
public class TestDemo {
    @Test
    public void testAddBook() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);

        Book book = new Book();
        book.setId("1");
        book.setName("java");
        book.setStatus(0);
        bookService.addBook(book);
    }
}
