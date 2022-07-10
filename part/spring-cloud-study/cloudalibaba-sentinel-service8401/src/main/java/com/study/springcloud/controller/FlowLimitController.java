package com.study.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        // 测试限流并发线程数
//        TimeUnit.MILLISECONDS.sleep(2000);
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        // 测试排队限流效果
        log.info("{}\t...testB", Thread.currentThread().getName());
        return "------testB";
    }


    @GetMapping("/testD")
    public String testD() {
        // 熔断慢调用比例
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 熔断 异常比例
        int age = 10 / 0;
        log.info("testD 测试RT");

        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "------testE 测试异常数";
    }


    @GetMapping("/testHotKey")
    // 这里注意没有斜杠，且名称是任意的，但是要求名称唯一
    // 该注解是对sentinel的dashboard负责的
    // 当没有blockHandler是会出现错误白页
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotkey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String deal_testHotkey(String p1, String p2, BlockException exception) {
        // sentinel的系统默认提示都是：blocked by sentinel(flow limiting)
        return "------deal_testHotkey,(；′⌒`)";
    }
}
