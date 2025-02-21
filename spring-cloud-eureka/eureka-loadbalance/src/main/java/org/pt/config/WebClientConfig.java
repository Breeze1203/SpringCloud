package org.pt.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName WebClientConfig
 * @Author pt
 * @Description
 * @Date 2025/2/16 23:06
 **/
@Configuration
@LoadBalancerClient(value = "provider", configuration = CustomLoadBalancerConfiguration.class)
public class WebClientConfig {

    @LoadBalanced
    @Bean
    RestTemplate loadBalanced() {
        return new RestTemplate();
    }


}
