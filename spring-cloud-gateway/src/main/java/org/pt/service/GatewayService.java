package org.pt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @ClassName GatewayService
 * @Author pt
 * @Description
 * @Date 2025/3/19 14:34
 **/
@Service(value = "GatewayService")
public class GatewayService {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    public Mono<List<RouteDefinition>> getAllGatewayInfo() {
        // 获取所有路由定义
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        // 非阻塞打印调试信息
        return routeDefinitions
                .collectList(); // 收集为List并返回Mono
    }
}
