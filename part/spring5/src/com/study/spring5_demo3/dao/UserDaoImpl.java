package com.study.spring5_demo3.dao;

import com.study.spring5_demo1.User;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/4
 * @Version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("dao add.....");
    }
}
