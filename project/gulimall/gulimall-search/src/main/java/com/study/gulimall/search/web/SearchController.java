package com.study.gulimall.search.web;

import com.study.gulimall.search.service.MallSearchService;
import com.study.gulimall.search.vo.SearchParam;
import com.study.gulimall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    /**
     * springmvc自动将页面提交过来的所有请求查询参数封装成指定的对象
     *
     * @param searchParam
     * @param model
     * @return
     */
    @GetMapping({"/list.html"})
    public String indexPage(SearchParam searchParam,
                            Model model) {
        // 根据页面传递过来的参数去es中检索商品
        SearchResult result = mallSearchService.search(searchParam);
        model.addAttribute("result", result);
        return "list";
    }
}
