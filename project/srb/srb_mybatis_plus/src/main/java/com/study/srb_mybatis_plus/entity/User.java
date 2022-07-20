package com.study.srb_mybatis_plus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class User {
    // 如果字段本身就叫id，并且数据库主键也叫id，那么可以不用加任何注解
    //     默认就是雪花算法
//    @TableId(type = IdType.ASSIGN_ID)
//    private Long id;
    // mp会将叫id的属性识别为主键字段，如果没有属性叫id，那么需要用@TableId标识一下哪个字段是id
//    @TableId
//    private Long uid;
    // 或者还叫id，但是用value值为uid
    @TableId(value = "uid")
    private Long id;

    @TableField(value = "username")// 必须添加的
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Integer age;

    private String email;

    //    @TableField("create_time")// 多此一举
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    @TableField(fill = FieldFill.UPDATE)// 只有更新才会自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)// 增加和更新都会自动填充
    private LocalDateTime updateTime;


    // 阿里的开发规范中有说明：is_xxxx在代码中不建议把is带过来而是直接使用xxxx
    @TableField("is_deleted")
    // 逻辑删除字段
    // 注意逻辑删除不会对插入语句做修改，因此需要使用‘自动填充策略/手动set/数据库默认’这三总方式中的一种，官方推荐使用数据库默认
    @TableLogic
    private Boolean deleted;// false->0:未删除；true->1:已删除


}
