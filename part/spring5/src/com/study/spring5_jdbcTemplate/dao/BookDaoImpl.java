package com.study.spring5_jdbcTemplate.dao;

import com.study.spring5_jdbcTemplate.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName BookDaoImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/7
 * @Version 1.0
 */
@Repository
public class BookDaoImpl implements BookDao {

    // 注入jdbcTemplate,这个对象是在配置文件中配置的，不是用注解配置的
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        // 1.创建sql语句
        String sql = "insert into book values(?,?,?)";

        // 2调用jdbcTemplate方法实现
        // int update = jdbcTemplate.update(sql, book.getId(), book.getName(), book.getStatus());
        Object[] args = {book.getId(), book.getName(), book.getStatus()};
        int update = jdbcTemplate.update(sql, args);

        // 3.打印执行结果
        System.out.println(update);
    }
}
