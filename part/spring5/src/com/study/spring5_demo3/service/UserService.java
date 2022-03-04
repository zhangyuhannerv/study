package com.study.spring5_demo3.service;

import com.study.spring5_demo3.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
@Service
// @Controller
// @Repository
public class UserService {
    // 定义dao类型属性
    // 注意使用注解时，不需要手动添加set(),这点和配置文件不同
    // 根据类型自动注入
    // @Autowired
    // @Qualifier必须和@Autowired配合使用，不能单独使用。因为UserDao接口有多个实现类，只使用@Autowired根据类型不知道找哪个实现类
    // @Qualifier的默认值就是属性值，这里使用默认值userDao还是找不到bean,所以要指定名称
    // @Qualifier(value = "userDaoImpl2")

    // @Resource既可以根据类型注入，也可以根据名称注入。
    // 注意事项1：@Resource时javax的，不是spring的。spring不推荐使用@Resource
    // 注意事项2：单独的@Resource，不写name。默认是先按照类型自动注入，找到多个类型的时候，再根据名称自动注入。如果写了name属性，那么就是直接根据名称自动注入，不再先根据类型了
    // 根据类型自动注入
    // @Resource
    // 根据名称自动注入
    @Resource(name = "userDaoImpl2")
    private UserDao userDao;


    // 注意@Value同样不需要手动写set(),这个和配置文件需要显式定义set()不同
    @Value("zhang")
    private String name;
    @Value("12")
    private int num;

    public void add() {
        System.out.println("service add");
        userDao.add();
    }

    public void printProp() {
        System.out.println(name);
        System.out.println(num);
    }
}
