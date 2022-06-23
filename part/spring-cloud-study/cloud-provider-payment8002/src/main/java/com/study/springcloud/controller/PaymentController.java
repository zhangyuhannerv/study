package com.study.springcloud.controller;

import com.study.springcloud.entities.CommonResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("****插入结果：{}", result);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据成功,端口号：" + serverPort, result);
        } else {
            return new CommonResult<>(500, "插入数据失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****查询结果：{}", payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功,端口号：" + serverPort, payment);
        } else {
            return new CommonResult<>(500, "查询失败,没有对应记录", payment);
        }
    }

    /**
     * 测试手写轮询算法的接口
     */
    @GetMapping("/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
