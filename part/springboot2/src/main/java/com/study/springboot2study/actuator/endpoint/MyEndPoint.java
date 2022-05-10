package com.study.springboot2study.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyEndPoint
 * @Description 自定义一个新的监控端点
 * 注意：自定义的端点，目前不参与yml里的配置，即总是开启的。
 * 如果想让yml参与管理，那么需要属性绑定@ConfigurationProperties
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */

@Component
@Endpoint(id = "myEndPoint")
public class MyEndPoint {

    // 这是一个端点的读操作
    // 注意：读操作的方法实际上是读取，所以方法里面不能有传参
    @ReadOperation
    public Map<String, Object> getDockerInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("dockerStatus", "UP");
        map.put("dockerVersion", "6.0.0");
        return map;
    }

    // 这是一个端点的写操作（想要测试，去jmx操作）
    // 它是参与到线上的管理功能，能对应用/服务做出一些改变
    @WriteOperation
    public void stopDocker() {
        System.out.println("docker stopped...");
    }
}
