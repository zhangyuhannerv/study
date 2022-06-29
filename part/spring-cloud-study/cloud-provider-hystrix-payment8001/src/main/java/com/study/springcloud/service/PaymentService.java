package com.study.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问，肯定ok
     */
    public String paymentInfo_ok(Integer id) {
        // 测试通配降级方法
        int age = 10 / 0;
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_ok,id：" + id + "\tO(∩_∩)O哈哈~";
    }

    /**
     * 执行时间超过3秒钟做降级处理(这里只是演示producer降级。但是服务降级一般放在consumer,客户端)
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            // 规定3秒钟以内就是正常的业务逻辑
            // 超过3秒钟是超时错误，进入到降级方法
            // 降级处理是使用一个单独的线程池
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
//        int age = 10 / 0;// 测试异常会不会降级
        int timeNumber = 2;// 测试超时会不会降级
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id：" + id + "\tO(∩_∩)O哈哈~ 耗时(秒)" + timeNumber + "秒钟";
    }

    /**
     * 降级方法（上面抛异常了或者上面执行超时了）
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + ",系统繁忙（执行超时）或者运行报错,paymentInfo_TimeOutHandler,id：" + id + "\t(；′⌒`)";
    }

    // ============熔断测试===============

    /**
     * 服务熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            // 10秒钟之内10次访问或以上，失败率达到60%那么就熔断
            // 10秒钟之内访问次数没有达到10次，那么全部访问失败也不会触发熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数，默认20
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围,默认10000（10s）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸，默认50
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功,流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " + id;
    }
}
