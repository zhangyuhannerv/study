package com.study.spring5_transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AccountDaoImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/9
 * @Version 1.0
 */
@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addMoney() {
        String sql = "update account set money = money + ? where username = ?";
        jdbcTemplate.update(sql, 100, "mary");
    }

    @Override
    public void reduceMoney() {
        String sql = "update account set money = money - ? where username = ?";
        jdbcTemplate.update(sql, 100, "lucy");
    }
}
