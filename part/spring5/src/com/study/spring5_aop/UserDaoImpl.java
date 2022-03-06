package com.study.spring5_aop;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */
public class UserDaoImpl implements UserDao {
    @Override
    public int add(int a, int b) {
        System.out.println("add方法正在被执行");
        return a + b;
    }

    @Override
    public String update(String id) {
        System.out.println("update方法正在被执行");
        return id;
    }
}
