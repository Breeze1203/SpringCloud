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
@LoadBalancerClient(value = "provider-round", configuration = RoundLoadBalancerConfiguration.class)
public class RoundClientConfig {

    @LoadBalanced
    @Bean(name = "round")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .build();
    }

}
