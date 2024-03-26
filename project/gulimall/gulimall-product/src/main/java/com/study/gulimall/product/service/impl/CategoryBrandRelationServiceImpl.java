package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.BrandDao;
import com.study.gulimall.product.dao.CategoryBrandRelationDao;
import com.study.gulimall.product.dao.CategoryDao;
import com.study.gulimall.product.entity.BrandEntity;
import com.study.gulimall.product.entity.CategoryBrandRelationEntity;
import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.BrandService;
import com.study.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryBrandRelationDao relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        categoryBrandRelation.setBrandName(brandEntity.getName());

        Long catelogId = categoryBrandRelation.getCatelogId();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setCatelogName(categoryEntity.getName());

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String brandName) {
        CategoryBrandRelationEntity item = new CategoryBrandRelationEntity();
        item.setBrandName(brandName);
        this.update(item,
                new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategory(Long catId, String categoryName) {
        this.baseMapper.updateCategory(catId, categoryName);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(CategoryBrandRelationEntity::getCatelogId, catId);
        List<CategoryBrandRelationEntity> relationList = relationDao.selectList(qw);
        List<BrandEntity> brandList = relationList.stream().map(item -> {
            BrandEntity brand = brandDao.selectById(item.getBrandId());
            return brand;
        }).collect(Collectors.toList());
        return brandList;
    }

}
