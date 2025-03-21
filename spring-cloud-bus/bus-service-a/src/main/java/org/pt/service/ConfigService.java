package org.pt.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName ConfigService
 * @Author pt
 * @Description
 * @Date 2025/3/21 11:25
 **/
@Service
public class ConfigService {
    // 本地文件存储目录（可通过配置文件动态设置）
    private static final String CONFIG_DIR = "src/main/resources/config/";

    public void updateConfig(String suffix, String content) throws IOException {
        if (!"dev".equals(suffix) && !"prod".equals(suffix)) {
            throw new IllegalArgumentException("Invalid suffix. Must be 'dev' or 'prod'.");
        }
        // 构造文件路径
        String fileName = "bus-" + suffix + ".properties";
        Path filePath = Paths.get(CONFIG_DIR, fileName);
        // 确保目录存在
        Files.createDirectories(filePath.getParent());
        // 覆盖写入新内容
        Files.writeString(filePath, content);
    }
}
