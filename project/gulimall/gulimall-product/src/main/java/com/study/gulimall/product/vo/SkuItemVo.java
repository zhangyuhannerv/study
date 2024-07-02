package com.study.gulimall.product.vo;

import com.study.gulimall.product.entity.SkuImagesEntity;
import com.study.gulimall.product.entity.SkuInfoEntity;
import com.study.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    // sku基本信息获取 pms_sku_info
    private SkuInfoEntity info;
    // sku的图信息获取 pms_sku_images
    private List<SkuImagesEntity> images;
    // spu的销售属性组合
    private List<SkuItemSaleAttrsVo> saleAttr;
    // 获取spu的介绍
    private SpuInfoDescEntity desp;
    // 获取spu的规格参数信息
    private List<SpuItemAttrGroupVo> groupAttrs;
}
