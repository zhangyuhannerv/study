package com.zh.ExcelDemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName Sstjq
 * @Description 伸缩调节器字典表
 * @Author Zhangyuhan
 * @Date 2022/3/30
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_jcsj_xl_sstjq")
public class Sstjq {
    @TableField("id")
    private String id;
    @TableField("xianbie")
    private String xianbie;
    @TableField("xingbie")
    private String xingbie;
    @TableField("station")
    private String station;
    @TableField("code")
    private String code;
    @TableField("type")
    private String type;
    @TableField("jdjd_lc")
    private BigDecimal jdJdLc;
    @TableField("bz")
    private String bz;
    @TableField("creat_time")
    private String createTime;
    @TableField("creat_per")
    private String createPer;

    @TableField(exist = false)
    private String xianbieStr;
    @TableField(exist = false)
    private String xingbieStr;
    @TableField(exist = false)
    private String stationStr;
    @TableField(exist = false)
    private String typeStr;

}
