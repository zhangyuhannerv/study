package com.study.gulimall.search.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class IndexController {

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        return "index";
    }
}
