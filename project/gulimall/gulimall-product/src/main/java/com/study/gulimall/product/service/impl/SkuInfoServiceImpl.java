package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.SkuInfoDao;
import com.study.gulimall.product.entity.SkuInfoEntity;
import com.study.gulimall.product.service.SkuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

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

}
