package com.study.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("CLOUD-PROVIDER-HYSTRIX-PAYMENT")
@RequestMapping("/payment")
public interface PaymentHystrixService {
    @GetMapping("/hystrix/ok/{id}")
    String paymentInfo_ok(@PathVariable("id") Integer id);

    @GetMapping("/hystrix/timeOut/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
