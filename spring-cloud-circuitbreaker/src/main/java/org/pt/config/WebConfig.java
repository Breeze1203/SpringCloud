package org.pt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @ClassName WebConfig
 * @Author pt
 * @Description
 * @Date 2025/2/19 09:54
 **/
@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
