package com.study.springboot2study.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @ClassName AppInfo
 * @Description 自己构建actuator里的info信息
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */
@Component
public class AppInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {

        builder.withDetail("msg", "你好")
                .withDetail("hello", "zyh")
                .withDetails(Collections.singletonMap("word", 66600));
    }
}
