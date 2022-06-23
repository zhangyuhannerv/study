package com.study.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface ILoadBalancer {
    ServiceInstance chooseServiceInstance(List<ServiceInstance> serviceInstances);
}
