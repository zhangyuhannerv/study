package com.study.springboot2study.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyComponentHealthIndicator
 * @Description 自定义监控的健康端点(给健康端点加个新的展示信息)
 * 注意这个新的展示信息（组件）的名字是HealthIndicator的前半部分：MyComponent
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */

@Component
public class MyComponentHealthIndicator extends AbstractHealthIndicator {
    /**
     * 编写真实的检查方法
     *
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String, Object> map = new HashMap<>();

        if (1 == 1) {
            // if (1 == 2) {
            builder.up();// 健康的
            // builder.status(Status.UP);// 健康的另一种写法

            map.put("count", 1);
            map.put("ms", 100);
        } else {
            builder.down();// 不健康的
            // builder.status(Status.OUT_OF_SERVICE);// 不健康（服务离线）
            // builder.status(Status.DOWN);// 不健康（服务不可用）

            map.put("error", "连接超时");
            map.put("ms", 3000);
        }

        // 在健康的基础上，加上一些详细信息
        builder.withDetail("code", 500)
                .withDetail("msg", "不健康")
                .withDetails(map);
    }
}
