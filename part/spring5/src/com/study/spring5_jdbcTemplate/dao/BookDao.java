package com.study.spring5_jdbcTemplate.dao;

import com.study.spring5_jdbcTemplate.entity.Book;

import java.util.List;

public interface BookDao {
    // 添加
    void add(Book book);

    // 修改
    void update(Book book);

    // 删除
    void delete(String id);

    // 查询某个具体的值
    int selectCount();

    Book selectOne(String id);

    List<Book> selectAll();
}
