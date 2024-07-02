package com.study.gulimall.product.dao;

import com.study.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gulimall.product.vo.SkuItemSaleAttrsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author ZhangYuhan
 * @email zhangyuhannerv@gmail.com
 * @date 2023-12-08 17:06:12
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrsVo> getSaleAttrsBySpuId(Long spuId);
}
