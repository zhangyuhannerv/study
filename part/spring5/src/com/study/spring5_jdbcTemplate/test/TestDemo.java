package com.study.spring5_jdbcTemplate.test;

import com.study.spring5_jdbcTemplate.entity.Book;
import com.study.spring5_jdbcTemplate.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

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

        // 添加
        Book book = new Book();
        book.setId("1");
        book.setName("java");
        book.setStatus(0);
        bookService.addBook(book);
    }

    @Test
    public void testUpdateBook() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);

        // 修改
        Book book = new Book();
        book.setId("1");
        book.setName("python");
        book.setStatus(1);
        bookService.update(book);
    }

    @Test
    public void testDeleteBook() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);

        // 删除
        bookService.delete("1");
    }

    @Test
    public void testSelectCount() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        System.out.println("查询到的数量是" + bookService.selectCount());
    }

    @Test
    public void selectOne() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        // 实践证明，必须有对象才能正确打印，否则报错。查id为1莫得问题，查id为2就报错
        System.out.println("查询到的对象是" + bookService.selectOne("1"));
    }

    @Test
    public void selectAll() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        // 实践证明，必须有对象才能正确打印，否则报错。查id为1莫得问题，查id为2就报错
        System.out.println(bookService.selectAll());
    }

    @Test
    public void batchAdd() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        Object[] o1 = {3, "js", 0};
        Object[] o2 = {4, "c++", 0};
        Object[] o3 = {5, "c#", 0};
        List<Object[]> list = Arrays.asList(o1, o2, o3);
        bookService.batchAdd(list);
    }

    @Test
    public void batchUpdate() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        Object[] o1 = {"js_edit", 1, 3};
        Object[] o2 = {"c++_edit", 1, 4};
        Object[] o3 = {"c#_edit", 1, 5};
        List<Object[]> list = Arrays.asList(o1, o2, o3);
        bookService.batchUpdate(list);
    }

    @Test
    public void batchDelete() {
        ApplicationContext app = new ClassPathXmlApplicationContext("com/study/spring5_jdbcTemplate/bean.xml");
        BookService bookService = app.getBean("bookService", BookService.class);
        Object[] o1 = {3};
        Object[] o2 = {4};
        Object[] o3 = {5};
        List<Object[]> list = Arrays.asList(o1, o2, o3);
        bookService.batchDelete(list);
    }

}
