package org.pt.controller;

import org.pt.resp.Response;
import org.pt.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName
 * @Author pt
 * @Description
 * @Date 2025/2/20 10:06
 **/
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/all")
    public Mono<Response<List<RouteDefinition>>> getGatewayInfo() {
        return gatewayService.getAllGatewayInfo()
                .map(routes -> new Response<>(200, "Success", routes))  // 成功时返回code=200和数据
                .defaultIfEmpty(new Response<>(200, "No routes found", Collections.emptyList()))  // 空结果时返回空列表
                .onErrorResume(e -> {
                    // 错误处理
                    System.err.println("Error fetching gateway info: " + e.getMessage());
                    return Mono.just(new Response<>(500, "Internal Server Error: " + e.getMessage()));
                });
    }
}