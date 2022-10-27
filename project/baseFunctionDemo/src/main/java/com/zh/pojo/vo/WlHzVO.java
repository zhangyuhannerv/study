package com.zh.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 物料汇总DTO
 */
@Data
public class WlHzVO {
    /**
     * 预算科目
     */
    private String yskm;

    /**
     * 物料属性
     */
    private String wlsx;

    /**
     * 物资编码
     */
    private String wlbm;

    /**
     * 物料名称
     */
    private String wlmc;

    /**
     * 规格型号
     */
    private String ggxh;

    /**
     * 计量单位
     */
    private String dw;

    /**
     * 预算单价
     */
    private BigDecimal dj;

    /**
     * 数量
     */
    private BigDecimal sl;

    /**
     * 合计
     */
    private BigDecimal hj;

    /**
     * 燕房线合计数量
     */
    private BigDecimal yfHjSl = BigDecimal.ZERO;

    /**
     * 机场线合计数量
     */
    private BigDecimal jcHjSl = BigDecimal.ZERO;

    /**
     * 19号线合计数量
     */
    private BigDecimal _19HjSl = BigDecimal.ZERO;

    /**
     * 燕房线路数量
     */
    private BigDecimal yfXlSl = BigDecimal.ZERO;

    /**
     * 燕房土建数量
     */
    private BigDecimal yfTjSl = BigDecimal.ZERO;

    /**
     * 机场线路数量
     */
    private BigDecimal jcXlSl = BigDecimal.ZERO;

    /**
     * 机场土建数量
     */
    private BigDecimal jcTjSl = BigDecimal.ZERO;

    /**
     * 19线路数量
     */
    private BigDecimal _19XlSl = BigDecimal.ZERO;

    /**
     * 19土建数量
     */
    private BigDecimal _19TjSl = BigDecimal.ZERO;

    /**
     * 计算合计
     */
    public void calHj() {
        this.yfHjSl = this.yfXlSl.add(this.yfTjSl);
        this.jcHjSl = this.jcXlSl.add(this.jcTjSl);
        this._19HjSl = this._19XlSl.add(this._19TjSl);
        this.sl = this.yfHjSl.add(this.jcHjSl).add(this._19HjSl);
        this.hj = this.sl.multiply(this.dj).setScale(3, RoundingMode.HALF_UP);
    }


}
