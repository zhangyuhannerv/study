package com.study.srb_mybatis_plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {
    //     默认就是雪花算法
//    @TableId(type = IdType.ASSIGN_ID)
//    private Long id;
    // mp会将叫id的属性识别为主键字段，如果没有属性叫id，那么需要用@TableId标识一下哪个字段是id
    @TableId
    private Long uid;
    private String name;
    private Integer age;
    private String email;
}
