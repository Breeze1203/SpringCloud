package org.pt.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RandomClientConfig
 * @Author pt
 * @Description
 * @Date 2025/2/16 23:06
 **/
@Configuration
@LoadBalancerClient(value = "provider-random", configuration = RandomLoadBalancerConfiguration.class)
public class RandomClientConfig {

    @LoadBalanced
    @Bean(name = "random")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .build();
    }

}
