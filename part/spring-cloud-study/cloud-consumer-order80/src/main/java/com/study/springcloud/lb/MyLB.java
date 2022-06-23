package com.study.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MyLB implements ILoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;

        } while (!this.atomicInteger.compareAndSet(current, next));
        log.info("*****访问次数next:{}", next);
        return next;
    }

    @Override
    public ServiceInstance chooseServiceInstance(List<ServiceInstance> serviceInstances) {
        int idx = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(idx);
    }
}
