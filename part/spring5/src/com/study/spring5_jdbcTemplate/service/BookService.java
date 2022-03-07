package com.study.spring5_jdbcTemplate.service;

import com.study.spring5_jdbcTemplate.dao.BookDao;
import com.study.spring5_jdbcTemplate.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
