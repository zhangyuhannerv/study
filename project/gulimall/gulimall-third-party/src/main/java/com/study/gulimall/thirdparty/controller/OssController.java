package com.study.gulimall.thirdparty.controller;


import com.aliyun.oss.OSSClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OSSClient ossClient;


    @RequestMapping("/policy")
    public Map<String, Object> policy() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
