package org.pt.config;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RouteConfig
 * @Author pt
 * @Description 路由配置类 java config配置
 * @Date 2025/2/20 10:00
 **/
@Configuration
public class RouteConfig {

    private final CustomLoggingFilterFactory customLoggingFilterFactory;

    public RouteConfig(CustomLoggingFilterFactory customLoggingFilterFactory) {
        this.customLoggingFilterFactory = customLoggingFilterFactory;
    }

    /*
    By registering the customizer as a bean,
    it will be automatically applied to the HTTP client used by the gateway
     */
    @Bean
    public HttpClientCustomizer myHttpClientCustomizer() {
        return new MyHttpClientCustomizer();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Weight High Route
                .route("weight_high", r -> r
                        .weight("group1", 8)
                        .and()
                        .path("/weight_two/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Source", "Gateway") // 内置过滤器
                                .filter(customLoggingFilterFactory.apply(new CustomLoggingFilterFactory.Config())) // 自定义过滤器
                        )
                        .uri("http://localhost:8082"))

                // Weight Low Route
                .route("weight_low", r -> r
                        .weight("group1", 2)
                        .and()
                        .path("/weight_two/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Source", "Gateway") // 内置过滤器
                                .filter(customLoggingFilterFactory.apply(new CustomLoggingFilterFactory.Config())) // 自定义过滤器
                        )
                        .uri("http://localhost:8081"))

                .build();
    }


}

@Component
class CustomLoggingFilterFactory extends AbstractGatewayFilterFactory<CustomLoggingFilterFactory.Config> {

    public CustomLoggingFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 前置逻辑：打印请求信息
            ServerHttpRequest request = exchange.getRequest();
            System.out.println("Request to " + request.getURI() + " at " + java.time.LocalDateTime.now());
            // 继续执行过滤器链
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // 后置逻辑：添加响应头
                exchange.getResponse().getHeaders().add("X-Custom-Header", "Processed by Gateway");
            }));
        };
    }

    // 配置类（可选，用于传递参数）
    public static class Config {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
