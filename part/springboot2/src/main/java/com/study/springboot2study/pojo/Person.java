package com.study.springboot2study.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Person
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/13
 * @Version 1.0
 */
@Data
public class Person {
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
}
