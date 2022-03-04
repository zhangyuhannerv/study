package com.study.spring5_demo3.dao;

import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDaoImpl2
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/4
 * @Version 1.0
 */
@Repository
public class UserDaoImpl2 implements UserDao {
    @Override
    public void add() {
        System.out.println("userDaoImpl2 add .....");
    }
}
