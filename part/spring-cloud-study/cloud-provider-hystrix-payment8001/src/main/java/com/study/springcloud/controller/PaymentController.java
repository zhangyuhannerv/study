package com.study.springcloud.controller;

import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable Integer id) {
        String result = paymentService.paymentInfo_ok(id);
        log.info("***result:{}", result);
        return result;
    }

    @GetMapping("/hystrix/timeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("***result:{}", result);
        return result;
    }

    /**
     * 熔断接口
     */
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("*******result:" + result);
        return result;
    }
}
