package com.study.springcloud.controller;

import com.study.springcloud.entities.CommonResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.IPaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class OrderFeignController {
    @Autowired
    private IPaymentFeignService paymentFeignService;


    @RequestMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        // 客户端一般默认等待1秒钟（注意，这是feign默认配置，如果服务端的响应时间超过1秒，那么就会报错。如果不想要这种效果。需要在yml手动配置feign的超时控制）
        // 但是这里故意让服务端等待了3秒钟
        return paymentFeignService.paymentFeignTimeout();
    }

}
