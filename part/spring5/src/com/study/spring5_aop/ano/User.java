package com.study.spring5_aop.ano;

import org.springframework.stereotype.Component;

/**
 * @ClassName User
 * @Description 被增强类
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */
@Component
public class User {
    public void add() {
        // 手动添加异常，测试异常通知
        // int i = 12 / 0;
        System.out.println("add....");
    }
}
