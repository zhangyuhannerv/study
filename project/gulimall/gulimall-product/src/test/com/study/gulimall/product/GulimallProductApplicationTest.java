package com.study.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.gulimall.product.entity.BrandEntity;
import com.study.gulimall.product.service.BrandService;
import com.study.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GulimallProductApplicationTest {
    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("遥遥领先");
        brandEntity.setName("华为");
        brandService.updateById(brandEntity);
        System.out.println("保存成功");
    }

    @Test
    public void testList() {
        QueryWrapper ew = new QueryWrapper();
        ew.eq("brand_id", 1L);
        System.out.println(brandService.list(ew));
    }

    @Test
    public void testCategoryPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225l);
        log.info(Arrays.toString(catelogPath));
    }
}
