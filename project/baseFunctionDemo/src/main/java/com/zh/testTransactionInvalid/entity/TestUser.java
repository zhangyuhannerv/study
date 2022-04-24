package com.zh.testTransactionInvalid.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName TestUser
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Data
@TableName("usertest")
public class TestUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
