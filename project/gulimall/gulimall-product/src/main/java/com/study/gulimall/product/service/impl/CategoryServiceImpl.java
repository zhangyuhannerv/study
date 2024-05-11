package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.CategoryDao;
import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryBrandRelationService;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.Catalog2Vo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2.组装成父子树形结构
        // 2.1找到所有的一级分类
        List<CategoryEntity> level1Menus =
                entities.stream()
                        .filter(e -> e.getParentCid() == 0)
                        .map(e -> {
                            e.setChildren(getChildren(e, entities));
                            return e;
                        })
                        .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                        .collect(Collectors.toList());


        return level1Menus;

    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {
        //todo 检查当前删除的菜单是否被别的地方引用

        // 逻辑删除
        baseMapper.deleteBatchIds(catIds);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParentPath(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[0]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        LambdaQueryWrapper<CategoryEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(CategoryEntity::getParentCid, 0);
        return baseMapper.selectList(qw);
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        // 查出所有1级分类
        List<CategoryEntity> level1Categorys = getLevel1Categorys();

        // 封装数据
        Map<String, List<Catalog2Vo>> collect = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 查询当前一级分类下的所有二级分类
            LambdaQueryWrapper<CategoryEntity> qw = new LambdaQueryWrapper<>();
            qw.eq(CategoryEntity::getParentCid, v.getCatId());
            List<CategoryEntity> categoryEntities = baseMapper.selectList(qw);

            // 封装数据
            List<Catalog2Vo> catalog2Vos = new ArrayList<>();

            if (!categoryEntities.isEmpty()) {
                catalog2Vos = categoryEntities.stream().map(l2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());

                    // 找当前分类的三级分类封装成vo
                    List<CategoryEntity> level3Catalog = baseMapper.selectList(
                            new LambdaQueryWrapper<CategoryEntity>()
                                    .eq(CategoryEntity::getParentCid, l2.getCatId()));

                    List<Catalog2Vo.Catalog3Vo> catalog3VoList = new ArrayList<>();
                    if (!level3Catalog.isEmpty()) {
                        catalog3VoList = level3Catalog.stream().map(l3 -> {
                            Catalog2Vo.Catalog3Vo catalog3Vo =
                                    new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return catalog3Vo;
                        }).collect(Collectors.toList());
                    }

                    catalog2Vo.setCatalog3List(catalog3VoList);

                    return catalog2Vo;
                }).collect(Collectors.toList());
            }

            return catalog2Vos;
        }));

        return collect;
    }

    private void findParentPath(Long catelogId, List<Long> paths) {
        // 收集当前节点id
        paths.add(catelogId);
        CategoryEntity category = this.getById(catelogId);
        if (category.getParentCid() != 0) {
            findParentPath(category.getParentCid(), paths);
        }
    }


    // 递归查找所有某个菜单的所有层级子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter(e -> Objects.equals(e.getParentCid(), root.getCatId()))
                .map(e -> {
                    e.setChildren(getChildren(e, all));
                    return e;
                })
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .collect(Collectors.toList());
        return children;
    }

}
