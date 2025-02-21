package org.pt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @ClassName CustomizeRepoConfig
 * @Author pt 自定义存储配置方式
 * @Description
 * @Date 2025/2/10 11:36
 **/
@Configuration
@Profile("custom")
public class CustomizeRepoConfig {
    @Bean
    public FileRepository fileRepository() {
        return new FileRepository();
    }

    @Bean
    public GitRepository gitRepository() {
        return new GitRepository();
    }
}
