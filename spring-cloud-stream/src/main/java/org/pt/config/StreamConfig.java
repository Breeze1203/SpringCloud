package org.pt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName StreamConfig
 * @Author pt
 * @Description
 * @Date 2025/3/6 16:31
 **/
@Configuration
public class StreamConfig {

    // 用于记录尝试发送的消息数量
    private final static AtomicInteger sendCount = new AtomicInteger(0);
    // 用于记录成功提交的消息数量
    private final static AtomicInteger committedCount = new AtomicInteger(0);
    // 用于记录消费的消息数量
    private final static AtomicInteger consumeCount = new AtomicInteger(0);
    // 用于记录消费失败或事务回滚的消息数量
    private final static AtomicInteger errorCount = new AtomicInteger(0);

    // 验证应用程序是否成功连接到 Kafka 并绑定主题 接受到消息进一步处理，发送到某个主题
    @Bean
    public Function<String, String> process() {
        return input -> "Processed: " + input;
    }

//    发送消息并确认消费者收到 发送消息
//    @Bean
//    public Supplier<String> producer() {
//        return () -> "Hello Kafka!";
//    }

    // 消费消息
    @Bean
    public Consumer<String> consumer() {
        return message -> System.out.println("Received: " + message);
    }

    @Bean
    public Consumer<List<String>> batchConsumer() {
        return messages -> messages.forEach(System.out::println);
    }

    // 注入 producer Bean 并定期触发
//    @Bean
//    public Supplier<String> triggerProducer(Supplier<String> producer) {
//        return producer;
//    }
//
//    //@Scheduled(fixedRate = 5000)  // 每5秒触发一次
//    public void scheduleProducer(Supplier<String> triggerProducer) {
//        triggerProducer.get();  // 触发 producer 发送消息
//    }

    /*
    手动提交
    */
    @Bean
    public Consumer<Message<String>> processAck() {
        return message -> {
            String payload = message.getPayload();
            System.out.println("Processing: " + payload);
            Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (payload.contains("error")) { // 模拟处理失败
                throw new RuntimeException("Simulated processing error");
            }
            if (acknowledgment != null) {
                System.out.println("Acknowledgment provided");
                acknowledgment.acknowledge();
            }
        };
    }

    // -------------------------------事务-------------------------
    @Bean
    @Transactional  // 启用事务支持
    public Supplier<String> transactionalProducer() {
        return () -> {
            sendCount.incrementAndGet();  // 记录尝试发送的消息
            String message = "Transactional Message";
            System.out.println("Producing: " + message);
            if (Math.random() < 0.5) {  // 50% 概率模拟回滚
                errorCount.incrementAndGet();  // 记录回滚失败
                throw new RuntimeException("Simulating transaction rollback");
            }
            committedCount.incrementAndGet();  // 记录成功提交
            return message;
        };
    }

    @Bean
    public Consumer<String> transactionalConsumer() {
        return message -> {
            System.out.println("Received: " + message);
            if (Math.random() < 0.5) {  // 50% 概率抛出异常模拟回滚
                errorCount.incrementAndGet();  // 记录回滚失败
                throw new RuntimeException("Simulating transaction rollback");
            }
            // 消费消息后增加计数
            int a = errorCount.get();         // 失败消息总数
            int b = consumeCount.incrementAndGet();  // 成功消费消息总数
            int c = sendCount.get();          // 生产消息总数
            int d = committedCount.get();     // 成功提交消息总数
            System.out.println(String.format("生产消息总数：%d，成功提交消息总数：%d，成功消费消息总数：%d，失败消息总数：%d", c, d, b, a));
        };
    }
}