package com.study.springcloud.service;

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
}
