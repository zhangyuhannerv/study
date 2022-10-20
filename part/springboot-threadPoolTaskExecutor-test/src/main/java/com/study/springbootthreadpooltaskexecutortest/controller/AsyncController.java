package com.study.springbootthreadpooltaskexecutortest.controller;

import com.study.springbootthreadpooltaskexecutortest.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public void async() {
        asyncService.executeAsync();
    }
}
