package com.study.springboot2study.controller;

import com.study.springboot2study.bean.TestAutoByProfile;
import com.study.springboot2study.bean.TestProfilePrecedence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestProfileController
 * @Description 测试springboot的配置文件的切换
 * @Author Zhangyuhan
 * @Date 2022/5/9
 * @Version 1.0
 */
@RestController
@RequestMapping("/testProfile")
public class TestProfileController {
    // 除了configurationProperties外，@Value也能从配置文件里取值
    // ':'的意思是，如果配置文件里没有相关的配置项，那么就会取相应的默认值
    @Value("${testProfile.name:李四}")
    private String name;


    // @Value还可以获得环境变量
    @Value("${JAVA_HOME}")
    private String javaHome;

    // @Value获取系统数据
    @Value("${os.name}")
    private String osName;// 操作系统名称


    @Autowired
    private TestAutoByProfile testAutoByProfile;

    @Autowired
    private TestProfilePrecedence testProfilePrecedence;

    @RequestMapping("/name")
    public String name() {
        return "hello," + name;
    }

    @RequestMapping("/testAutoByProfile")
    public TestAutoByProfile testAutoByProfile() {
        return testAutoByProfile;
    }

    @RequestMapping("/testAutoByProfileClass")
    public String testAutoByProfileClass() {
        // 当profile.active=dev时，返回TestAutoByProfile_A
        // 当profile.active=test时，返回TestAutoByProfile_B
        return testAutoByProfile.getClass().toString();
    }

    @RequestMapping("/javaHome")
    public String getJavaHome() {
        return javaHome;
    }

    @RequestMapping("/osName")
    public String osName() {
        return osName;
    }

    /**
     * 测试配置文件加载的优先级
     *
     * @return
     */
    @RequestMapping("/testPrecedence")
    public TestProfilePrecedence testProfilePrecedence() {
        return testProfilePrecedence;
    }

}
