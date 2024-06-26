package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.SkuInfoDao;
import com.study.gulimall.product.entity.SkuImagesEntity;
import com.study.gulimall.product.entity.SkuInfoEntity;
import com.study.gulimall.product.entity.SpuInfoDescEntity;
import com.study.gulimall.product.service.*;
import com.study.gulimall.product.vo.SkuItemSaleAttrsVo;
import com.study.gulimall.product.vo.SkuItemVo;
import com.study.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> qw = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        String brandId = (String) params.get("brandId");
        String catelogId = (String) params.get("catelogId");
        String min = (String) params.get("min");
        String max = (String) params.get("max");

        qw.and(StringUtils.isNotBlank(key), w -> {
                    w.eq(SkuInfoEntity::getSkuId, key).or().like(SkuInfoEntity::getSkuName, key);
                })
                .eq(StringUtils.isNotBlank(brandId) && !"0".equals(brandId), SkuInfoEntity::getBrandId, brandId)
                .eq(StringUtils.isNotBlank(catelogId) && !"0".equals(catelogId), SkuInfoEntity::getCatalogId, catelogId)
                .ge(StringUtils.isNotBlank(min), SkuInfoEntity::getPrice, min)
                .le(StringUtils.isNotBlank(max) && !"0".equals(max), SkuInfoEntity::getPrice, max);

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        LambdaQueryWrapper<SkuInfoEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(SkuInfoEntity::getSpuId, spuId);
        return baseMapper.selectList(qw);
    }

    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo vo = new SkuItemVo();
        // sku基本信息获取 pms_sku_info
        SkuInfoEntity skuInfoEntity = getById(skuId);
        vo.setInfo(skuInfoEntity);

        Long catalogId = skuInfoEntity.getCatalogId();
        Long spuId = skuInfoEntity.getSpuId();


        // sku的图信息获取 pms_sku_images
        List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
        vo.setImages(images);

        // spu的销售属性组合
        List<SkuItemSaleAttrsVo> saleAttrsVos = skuSaleAttrValueService.getSaleAttrsBySpuId(spuId);
        vo.setSaleAttr(saleAttrsVos);

        // 获取spu的介绍
        SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(spuId);
        vo.setDesp(spuInfoDescEntity);

        // 获取spu的规格参数信息
        List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
        vo.setGroupAttrs(attrGroupVos);

        return vo;
    }

}
