package org.pt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @ClassName HandleConfig
 * @Author pt
 * @Description
 * @Date 2025/3/11 14:47
 **/
@Configuration
public class HandleConfig {
    @Bean
    public Consumer<ErrorMessage> ErrorHandler() {
        return v -> {
            // 打印异常信息
            Throwable payload = v.getPayload();
            System.out.println("异常信息：------->" + payload.getMessage());
            // 打印原始消息
            Object message = v.getOriginalMessage().getPayload();
            System.out.println("原始消息：------->" + message);
            // 打印消息头
            MessageHeaders headers = v.getOriginalMessage().getHeaders();
            System.out.println("消息头：------->" + headers);
            // 示例：提取特定头信息
            String topic = (String) headers.get(KafkaHeaders.RECEIVED_TOPIC);
            System.out.println("消息来源主题：------->" + topic);
        };
    }
}
