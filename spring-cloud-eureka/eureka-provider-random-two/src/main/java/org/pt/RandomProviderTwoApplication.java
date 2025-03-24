package org.pt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RandomProviderTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RandomProviderTwoApplication.class);
    }
}