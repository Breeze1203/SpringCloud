package org.pt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class OauthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthClientApplication.class);
    }

    @Bean
    CommandLineRunner testRedis(RedisTemplate<String, String> redisTemplate) {
        return args -> {
            redisTemplate.opsForValue().set("testKey", "testValue");
            System.out.println("Redis value: " + redisTemplate.opsForValue().get("testKey"));
        };
    }
}