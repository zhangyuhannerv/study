package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.CategoryDao;
import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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

    // 地柜查找所有某个菜单的所有层级子菜单
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
