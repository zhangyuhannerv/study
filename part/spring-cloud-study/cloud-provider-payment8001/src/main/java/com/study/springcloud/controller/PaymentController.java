package com.study.springcloud.controller;

import com.study.springcloud.entities.CommonResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

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

    // 通过服务发现来获取当前服务信息
    @GetMapping("/discovery")
    public Object discovery() {
        // 获取所有的微服务名称
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****服务列表:{}", service);
        }
        // 获取某个微服务名称下面的全部实例
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        //看看discoveryClient具备那些信息
        return this.discoveryClient;
    }
}
