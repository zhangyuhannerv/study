package com.study.springboot2study;

import com.study.springboot2study.bean.Book;
import com.study.springboot2study.bean.Pet;
import com.study.springboot2study.bean.User;
import com.study.springboot2study.config.MyConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName MainApplication
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/21
 * @Version 1.0
 */

/**
 * 编写主程序类，固定写法
 *
 * @SpringBootApplication 告诉springboot这是一个springboot应用
 */
@SpringBootApplication
// 注意：自动扫描默认是运行类所在的包及其子包
// 如果想扫描别的兄弟包，可以扩大扫描范围,如下
// @SpringBootApplication(scanBasePackages = "com.study")

// 同时也可以排除某些自动配置(比如，如果redis数据库失效，并且只想成功的启动项目，那么可以在这里手动的排除redis的配置）
// @SpringBootApplication(exclude = RedisAutoConfiguration.class)

// 扫描原生的自定义的Servlet(和springboot扫描的一样,默认是启动类所在的包及其之下的子包）
@ServletComponentScan

// 不推荐mybatis使用这种扫描包的方式，推荐在接口行使用@Mapper注解
// @MapperScan(basePackages = {"com.study.springboot2study.**.mapper"})
public class MainApplication {
    public static void main(String[] args) {
        // 1.返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        /*// 2.查看容器里的组件
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
        // 3.从容器中获取组件(组件默认是单实例模式），即获取多少次都是获取的同一个
        Pet tom = run.getBean("tom", Pet.class);
        Pet tom1 = run.getBean("tom", Pet.class);
        Pet tom2 = run.getBean("tom", Pet.class);
        System.out.println((tom == tom1) && (tom1 == tom2));// 打印true


        // 4.获取到的对象是个代理对象com.study.springboot2study.config.MyConfig$$EnhancerBySpringCGLIB$$306f94ea@464a4442
        MyConfig bean = run.getBean(MyConfig.class);
        System.out.println(bean);

        // 5.验证配置类的方法是否被代理
        // 如果@Configuration(proxyBeanMethods = true),此时就是代理对象调用方法。springboot总会检查需要的组件是否在
        // 目的就是为了保持组件的单实例
        User user = bean.user01();
        User user1 = bean.user01();
        System.out.println(user == user1);// 打印true

        // 6.测试组件依赖
        User user01 = run.getBean("user01", User.class);
        Pet pet = user01.getPet();
        System.out.println(pet == tom);

        // 7.测试@Import
        String[] beanNamesForType = run.getBeanNamesForType(User.class);
        System.out.println(Arrays.toString(beanNamesForType));

        // 8.测试@ConditionalOnBean注解
        System.out.println(run.containsBean("book"));
        System.out.println(run.containsBean("person"));

        // 9测试@ImportResource注解
        System.out.println(run.containsBean("haha"));
        System.out.println(run.containsBean("hehe"));*/


        // 10.获取到当前的环境配置(下面两处获取到的所有信息，都可以使用@Value(${})直接获取到,详见TestProfileController)
        ConfigurableEnvironment environment = run.getEnvironment();
        // 10.1这是操作系统的环境变量
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        System.out.println(systemEnvironment);
        System.out.println("---");

        // 10.2获取系统的属性
        Map<String, Object> systemProperties = environment.getSystemProperties();
        System.out.println(systemProperties);
    }


}
