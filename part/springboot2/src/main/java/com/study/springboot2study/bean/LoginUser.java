package com.study.springboot2study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginUser
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String username;
    private String password;
}
