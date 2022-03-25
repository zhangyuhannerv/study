package com.study.springboot2study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName Pet
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/22
 * @Version 1.0
 */
@Data //getter()和setter()
@ToString // 重写toString()
@AllArgsConstructor// 全参构造器
@NoArgsConstructor// 无参构造器
public class Pet {
    private String name;
}
