package com.baseDemo.fileMonitorEchartsShow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
    public static final String GIS = "gis/";

    @GetMapping({"/", "/index", "/beetl"})
    public String beetl(Model model) {
        model.addAttribute("beetl", "测试一下通过模板引擎传递参数！");
        return "index.html";
    }

    @GetMapping("/baiduMap")
    public String baiduMap(Model model) {
        return GIS + "baiduMap.html";
    }

}
