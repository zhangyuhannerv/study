package com.study.spring5_jdbcTemplate.dao;

import com.study.spring5_jdbcTemplate.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public void update(Book book) {
        String sql = "update book set name = ?,status = ? where id = ?";
        Object[] args = {book.getName(), book.getStatus(), book.getId()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println(update);
    }

    @Override
    public void delete(String id) {
        String sql = "delete from book where id = ?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println(update);
    }

    @Override
    public int selectCount() {
        String sql = "select count(*) from book";
        return jdbcTemplate.queryForObject(sql, int.class);
    }

    @Override
    public Book selectOne(String id) {
        String sql = "select * from book where id = ?";
        // 调用方法
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
    }

    @Override
    public List<Book> selectAll() {
        String sql = "select * from book";
        // 调用方法
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public void batchAdd(List<Object[]> args) {
        String sql = "insert into book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, args);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdate(List<Object[]> args) {
        String sql = "update book set name = ?,status = ? where id = ?";
        int[] ints = jdbcTemplate.batchUpdate(sql, args);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDelete(List<Object[]> args) {
        String sql = "delete from book where id = ?";
        int[] ints = jdbcTemplate.batchUpdate(sql, args);
        System.out.println(Arrays.toString(ints));
    }
}
