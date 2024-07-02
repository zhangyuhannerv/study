package com.study.gulimall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuItemSaleAttrsVo {
    private Long attrId;
    private String attrName;
    private List<String> attrValues;
}
