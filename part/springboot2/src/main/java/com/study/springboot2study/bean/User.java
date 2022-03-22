package com.study.springboot2study.bean;

/**
 * @ClassName User
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/22
 * @Version 1.0
 */
public class User {
    private String user;
    private String age;
    private Pet pet;

    public User() {
    }

    public User(String user, String age) {
        this.user = user;
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
