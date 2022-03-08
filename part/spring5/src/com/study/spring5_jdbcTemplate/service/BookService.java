package com.study.spring5_jdbcTemplate.service;

import com.study.spring5_jdbcTemplate.dao.BookDao;
import com.study.spring5_jdbcTemplate.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookService
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/7
 * @Version 1.0
 */
@Service
public class BookService {
    // 注入dao
    @Autowired
    private BookDao bookDao;


    // 添加的方法
    public void addBook(Book book) {
        bookDao.add(book);
    }

    // 修改的方法
    public void update(Book book) {
        bookDao.update(book);
    }


    // 删除的方法
    public void delete(String id) {
        bookDao.delete(id);
    }

    // 查询返回一个值
    public int selectCount() {
        return bookDao.selectCount();
    }

    // 查询返回一个对象
    public Book selectOne(String id) {
        return bookDao.selectOne(id);
    }

    // 查询返回集合
    public List<Book> selectAll() {
        return bookDao.selectAll();
    }
}
