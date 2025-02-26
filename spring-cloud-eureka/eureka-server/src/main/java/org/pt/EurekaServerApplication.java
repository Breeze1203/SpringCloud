package org.pt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class);
        System.out.println("Starting GC test...");
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[1024 * 1024]; // 分配 1MB 内存
            System.out.println("Allocated " + i + " MB");
        }
    }
}