package com.study.springbootstarterhelloautoconfigure.hello.service;

import com.study.springbootstarterhelloautoconfigure.hello.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName HelloService
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/10
 * @Version 1.0
 */

/**
 * 默认不要放在容器中
 */
public class HelloService {
    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String name) {
        return helloProperties.getPrefix() + ":" + name + helloProperties.getSuffix();
    }
}
