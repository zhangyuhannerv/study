package com.study.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装页面所有可能传递过来的查询条件
 */
@Data
public class SearchParam {
    private String keyword;// 页面传递过来的全文匹配关键字
    private Long catalog3Id;// 三级分类的id
    /**
     * sort=saleCount_asc/desc 销量
     * sort=skuPrice_asc/desc 价格
     * sort=hotScore_asc/desc 综合评分（热度分）
     */
    private String sort;// 排序条件

    /**
     * 好多的过滤条件
     * hasStock 是否有货
     * hasStock=0/1
     * skuPrice 价格区间，以_拼接
     * skuPrice=1_500/_500/500_
     */
    private Integer hasStock;// 是否只显示有货(0:无库存 1:有库存),默认查询有库存的

    private String skuPrice;// 价格区间

    private List<Long> brandId;// 品牌id,可以传多个:url?brandId=1&brandId=2&brandId=3
    private List<String> attrs;// 按照属性进行筛选:url?attrs=1_安卓:苹果&attrs=2_2.5寸:6寸

    /**
     * 分页相关
     */
    private Integer pageNum = 1; // 页码（默认第一页）

}
