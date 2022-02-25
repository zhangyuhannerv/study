package com.study.spring5;

/**
 * @ClassName User
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/2/23
 * @Version 1.0
 */
public class User {
    private String userName;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public void add() {
        System.out.println("add.....");
    }
}
