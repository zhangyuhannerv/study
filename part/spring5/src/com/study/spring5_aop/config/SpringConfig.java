package com.study.spring5_aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName SpringConfig
 * @Description 怎么使用完全注解开发来配置aop代理
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */

@Configuration
/*开启组件扫描*/
@ComponentScan("com.study.spring5_aop")
/*开启Aspect生成代理对象，代替  <aop:aspectj-autoproxy/>*/
/*proxyTargetClass默认值是false，spring根据类是否实现接口来选择使用jdk还是cglib*/
/*当proxyTargetClass值为true时，所有代理强制使用cglib*/
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class SpringConfig {

}
