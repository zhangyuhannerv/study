package com.study.springboot2study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName Book
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/23
 * @Version 1.0
 */

@Data //getter()和setter()
@ToString // 重写toString()
@AllArgsConstructor// 全参构造器
@NoArgsConstructor// 无参构造器
public class Book {
    private String name;
}
