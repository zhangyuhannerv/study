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
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


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
    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<>()
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

    @SneakyThrows
    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo vo = new SkuItemVo();

        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            // sku基本信息获取 pms_sku_info
            SkuInfoEntity skuInfoEntity = getById(skuId);
            vo.setInfo(skuInfoEntity);
            return skuInfoEntity;
        }, executor);

        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            // spu的销售属性组合
            List<SkuItemSaleAttrsVo> saleAttrsVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            vo.setSaleAttr(saleAttrsVos);
        }, executor);

        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            // 获取spu的介绍
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            vo.setDesp(spuInfoDescEntity);
        }, executor);

        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            // 获取spu的规格参数信息
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
            vo.setGroupAttrs(attrGroupVos);
        }, executor);

        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            // sku的图信息获取 pms_sku_images
            List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
            vo.setImages(images);
        }, executor);

        // 等到所有异步任务都完成
        CompletableFuture.allOf(saleAttrFuture, descFuture, baseAttrFuture, imageFuture).get();

        return vo;
    }

}
