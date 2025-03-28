package org.pt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@RefreshScope //确保 message 在刷新事件后重新绑定
public class BServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BServiceApplication.class);
    }

    @Value("${bus:default-value}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}