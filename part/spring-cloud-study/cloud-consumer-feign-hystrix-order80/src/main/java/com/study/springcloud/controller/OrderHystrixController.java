package com.study.springcloud.controller;

import com.study.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderHystrixController {
    @Autowired
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable Integer id) {
        return paymentHystrixService.paymentInfo_ok(id);
    }

    @GetMapping("/payment/hystrix/timeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable Integer id) {
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }
}
