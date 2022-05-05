package com.study.springboot2study.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName TestUser
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/5
 * @Version 1.0
 */

@Data
@TableName("user")
public class TestUser {
    private Integer id;
    private String name;
    private Integer typeid;
}
