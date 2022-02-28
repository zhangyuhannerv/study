package com.study.spring5_demo1.service;

import com.study.spring5_demo1.dao.UserDaoImpl;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/2/25
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    // 创建UserDao类型属性，生成set方法
    private UserDaoImpl userDao;

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public void add() {
        System.out.println("service add................");

        // 原始的java方式
        // UserDao userDao = new UserDaoImpl();
        // userDao.update();

        userDao.update();
    }
}
