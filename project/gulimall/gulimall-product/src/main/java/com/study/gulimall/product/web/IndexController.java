package com.study.gulimall.product.web;

import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // 1.查出所有的一级分类
        List<CategoryEntity> list = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", list);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }
}
