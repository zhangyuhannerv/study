package com.zh.ExcelDemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 工程车辆表
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-04-08
 */
@TableName("djxt_engineering_car")
@Data
@EqualsAndHashCode
public class EngineeringCar implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 设备名称
     */
    @TableField("sb_name")
    private String sbName;

    /**
     * 设备编号
     */
    @TableField("sb_code")
    private String sbCode;

    /**
     * 状态
     */
    @TableField("zt")
    private Integer zt;

    /**
     * 修改人
     */
    @TableField("update_per")
    private String updatePer;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Timestamp updateTime;

    @TableField(exist = false)
    private String ztStr;

}
