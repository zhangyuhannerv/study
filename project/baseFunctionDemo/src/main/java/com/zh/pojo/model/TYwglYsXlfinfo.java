package com.zh.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 预算—修理费详情表
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ywgl_ys_xlfinfo")
public class TYwglYsXlfinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("jcjlid")
    private Integer jcjlid;

    /**
     * 预算科目
     */
    @TableField("yskm")
    private String yskm;

    /**
     * 物料属性
     */
    @TableField("wlsx")
    private String wlsx;

    /**
     * 物资编码
     */
    @TableField("wlbm")
    private String wlbm;

    /**
     * 物料名称
     */
    @TableField("wlmc")
    private String wlmc;

    /**
     * 规格型号
     */
    @TableField("ggxh")
    private String ggxh;

    /**
     * 计量单位
     */
    @TableField("dw")
    private String dw;

    /**
     * 预算单价
     */
    @TableField("dj")
    private BigDecimal dj;

    /**
     * 数量
     */
    @TableField("sl")
    private BigDecimal sl;

    /**
     * 合计
     */
    @TableField("hj")
    private BigDecimal hj;

    /**
     * 燕房合计
     */
    @TableField("sl_yf")
    private BigDecimal slYf;

    /**
     * 机场合计
     */
    @TableField("sl_jc")
    private BigDecimal slJc;

    /**
     * 19号合计
     */
    @TableField("sl_19h")
    private BigDecimal sl19h;

    /**
     * 第一季度合计
     */
    @TableField("sl_one")
    private BigDecimal slOne;

    /**
     * 第二季度合计
     */
    @TableField("sl_two")
    private BigDecimal slTwo;

    /**
     * 第三季度合计
     */
    @TableField("sl_three")
    private BigDecimal slThree;

    /**
     * 第四季度合计
     */
    @TableField("sl_four")
    private BigDecimal slFour;

    /**
     * 录入人
     */
    @TableField("lr_per")
    private String lrPer;

    /**
     * 线别
     */
    @TableField(exist = false)
    private String xianbie;

    /**
     * 专业
     */
    @TableField(exist = false)
    private String zy;

}
