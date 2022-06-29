package com.study.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class OrderHystrixController {
    @Autowired
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/payment/hystrix/ok/{id}")
    // 当全局降级和通配降级都存在时
    // 这里除了调用service之外有其他的异常，无论调用的service有没有异常，那么就会走全局降级
    // 这里如果除了调用service之外没有其他的异常，而调用的service有异常，那么就会走通配降级
    // 但是一般不会这么做，一般都是只写通配降级
    @HystrixCommand
//  关闭8001服务端，还可以测试通配降级方法
    public String paymentInfo_ok(@PathVariable Integer id) {
        String s = paymentHystrixService.paymentInfo_ok(id);
        // 测试全局降级和通配降级的优先顺序
//        int age = 10 / 0;
        return s;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            // 规定3秒钟以内就是正常的业务逻辑
            // 超过3秒钟是超时错误，进入到降级方法
            // 降级处理是使用一个单独的线程池
            // 注意除了超时，异常也会进入降级方法
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @GetMapping("/payment/hystrix/timeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable Integer id) {
//        int age = 10 / 0;// 测试异常导致的服务降级
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    /**
     * paymentInfo_TimeOut专属的降级方法
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + ",客户端80系统繁忙（执行超时）或者运行报错,paymentInfo_TimeOutHandler,id：" + id + "\t(；′⌒`)";
    }

    /**
     * 全局fallback,凡是使用了@HystrixCommand但是没有指定fallbackMethod的都会使用该降级方法
     */
    public String payment_global_fallbackMethod() {
        return "全局异常处理信息，请稍后再试试";
    }
}
