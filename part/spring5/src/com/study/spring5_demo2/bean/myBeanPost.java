package com.study.spring5_demo2.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @ClassName myBeanPost
 * @Description bean的后置处理器
 * @Author Zhangyuhan
 * @Date 2022/3/2
 * @Version 1.0
 */
public class myBeanPost implements BeanPostProcessor {
    // 在执行bean生命周期第三步初始化之前，要执行该方法
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("执行初始化之前，执行该3-（-1）方法");
        return bean;
    }

    // 在执行bean生命周期第三步初始化之后，要执行该方法
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("执行初始化之后，执行该3-（+1）方法");
        return bean;
    }
}
