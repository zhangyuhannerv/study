package com.study.springboot2study.bean;

import lombok.*;

/**
 * @ClassName User
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/22
 * @Version 1.0
 */
@Data //getter()和setter()
@ToString // 重写toString()
@AllArgsConstructor// 全参构造器
@NoArgsConstructor// 无参构造器
@EqualsAndHashCode// 重写对象相等和hashcode相等
public class User {
    private String user;
    private String age;
    private Pet pet;

    /**
     * 需要定制的构造器需要自己来写
     *
     * @param user
     * @param age
     */
    public User(String user, String age) {
        this.user = user;
        this.age = age;
    }
}
