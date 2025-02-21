package org.pt.config;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.Target;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import reactor.util.retry.Retry;

import java.lang.reflect.Method;

/**
 * @ClassName FeignConfig
 * @Author pt
 * @Description
 * @Date 2025/2/19 15:20
 **/
@Configuration
public class FeignConfig {
    // 日志支持
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    Retryer retryer(){
        return new Retryer.Default();
    }

}
