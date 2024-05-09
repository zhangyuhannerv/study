package com.study.gulimall.search.controller;

import com.study.common.exception.BizCodeEnum;
import com.study.common.to.es.SkuEsModule;
import com.study.common.utils.R;
import com.study.gulimall.search.service.ElasticSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {
    @Autowired
    ElasticSaveService elasticSaveService;

    /**
     * 上架商品
     */
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModule> skuEsModules) {
        Boolean b = false;
        try {
            b = elasticSaveService.productStatusUp(skuEsModules);
        } catch (Exception e) {
            log.error("ElasticSaveController商品上架错误:{}", e.getMessage());
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if (b) {
            return R.ok();
        } else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }
}
