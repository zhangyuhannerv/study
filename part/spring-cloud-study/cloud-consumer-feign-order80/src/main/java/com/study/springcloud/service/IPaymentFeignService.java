package com.study.springcloud.service;

import com.study.springcloud.entities.CommonResult;
import com.study.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 指明要调用的服务名称
@FeignClient("cloud-payment-service")
public interface IPaymentFeignService {
    // 这里整合服务端的接口
    // 可以理解为把原先在8001上controller上方法抽像成一个接口，原controller上的方法是该接口的实现类
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

}
