package com.study.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码方式编写网关
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLoacater(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route("path_route_study1", r -> r.path("/guonei").uri("http://news.baidu.com")).build();
    }
}
