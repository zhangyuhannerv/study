package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.to.SkuReductionTo;
import com.study.common.to.SpuBoundTo;
import com.study.common.to.es.SkuEsModule;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.common.utils.R;
import com.study.gulimall.product.dao.SpuInfoDao;
import com.study.gulimall.product.entity.*;
import com.study.gulimall.product.feign.CouponFeignService;
import com.study.gulimall.product.service.*;
import com.study.gulimall.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * todo 后续完善分布式事务
     *
     * @param vo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        // 保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);
        // 保存spu的图片描述 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);
        // 保存spu的图片集 pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);
        // 保存spu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(attrEntity.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());

        productAttrValueService.saveProductAttr(collect);
        // 保存spu的积分信息:跨服务，跨库叼你个gulimall_sms:sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        BeanUtils.copyProperties(bounds, spuBoundTo);
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
        }
        // 保存当前spu对应的所有sku
        List<Skus> skus = vo.getSkus();
        for (Skus item : skus) {
            // 保存sku的基本信息 pms_sku_info
            String defaultImg = "";
            for (Images img : item.getImages()) {
                if (img.getDefaultImg() == 1) {
                    defaultImg = img.getImgUrl();
                }
            }

            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(item, skuInfoEntity);
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setSkuDefaultImg(defaultImg);
            skuInfoService.saveSkuInfo(skuInfoEntity);
            Long skuId = skuInfoEntity.getSkuId();
            // 保存sku的图片信息 pms_sku_images
            List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                skuImagesEntity.setSkuId(skuId);
                skuImagesEntity.setImgUrl(img.getImgUrl());
                skuImagesEntity.setDefaultImg(img.getDefaultImg());
                return skuImagesEntity;
            }).filter(entity -> StringUtils.isNotBlank(entity.getImgUrl())).collect(Collectors.toList());

            skuImagesService.saveBatch(imagesEntities);
            // 保存sku的销售属性 pms_sku_sale_attr_value
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = item.getAttr().stream().map(a -> {
                SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(a, attrValueEntity);
                attrValueEntity.setSkuId(skuId);
                return attrValueEntity;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
            // 保存sku的优惠、满减信息 跨服务，跨库调用gulimall_sms:sms_sku_ladder,sms_sku_full_reduction,sms_member_price
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            BeanUtils.copyProperties(item, skuReductionTo);
            skuReductionTo.setSkuId(skuId);

            if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
                R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                if (r1.getCode() != 0) {
                    log.error("远程保存sku优惠信息失败");
                }
            }
        }


    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SpuInfoEntity> qw = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String brandId = (String) params.get("brandId");
        String catelogId = (String) params.get("catelogId");
        qw.and(StringUtils.isNotBlank(key), w -> {
                    w.eq(SpuInfoEntity::getId, key).or().like(SpuInfoEntity::getSpuName, key);
                })
                .eq(StringUtils.isNotBlank(status), SpuInfoEntity::getPublishStatus, status)
                .eq(StringUtils.isNotBlank(brandId) && !"0".equals(brandId), SpuInfoEntity::getBrandId, brandId)
                .eq(StringUtils.isNotBlank(catelogId) && !"0".equals(catelogId), SpuInfoEntity::getCatalogId, catelogId);
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), qw);
        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {
        List<SkuEsModule> upProducts = new ArrayList<>();
        // todo 查询当前sku所有的可以被检索的规格属性
        // 1.查出当前spuId对应的所有sku信息,品牌的名字
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
        // 2.封装每个sku的信息
        List<SkuEsModule> esModuleList = skus.stream().map(sku -> {
            // 组装需要的数据
            SkuEsModule esModule = new SkuEsModule();
            BeanUtils.copyProperties(sku, esModule);

            esModule.setSkuPrice(sku.getPrice());
            esModule.setSkuImg(sku.getSkuDefaultImg());
            // todo 发送远程调用，库存系统查询是否有库存
            // todo 热度评分，这里默认给0
            // 3.查询品牌和分类的名字信息
            BrandEntity brand = brandService.getById(esModule.getBrandId());
            esModule.setBrandName(brand.getName());
            esModule.setBrandImg(brand.getLogo());

            CategoryEntity category = categoryService.getById(esModule.getCatalogId());
            esModule.setCatalogName(category.getName());


            return esModule;
        }).collect(Collectors.toList());

        // todo 5.将数据发送给es进行保存
    }


}
