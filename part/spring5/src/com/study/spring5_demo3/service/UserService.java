package com.study.spring5_demo3.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/3
 * @Version 1.0
 */

/*在注解里面的value属性值可以省略不写，如果不写，那么默认值是类的名称的首字母小写 UserService->userService*/
// @Component(value = "userService")// 等价于<bean id="userService" class="com.study.spring5_demo3.service">

// @Controller,@Service,@Repository,@Component是完全等效的，名称不同只是为了开发方便
// @Service
// @Controller
@Repository
public class UserService {
    public void add() {
        System.out.println("service add");
    }
}
