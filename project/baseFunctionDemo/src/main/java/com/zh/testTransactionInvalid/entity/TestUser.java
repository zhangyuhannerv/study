package com.zh.testTransactionInvalid;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Data
@TableName("usertest")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
