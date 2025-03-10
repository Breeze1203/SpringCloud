package org.pt.config;

import org.apache.kafka.streams.kstream.KStream;
import org.pt.entity.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

/**
 * @ClassName StreamConfig
 * @Author pt
 * @Description
 * @Date 2025/3/6 16:31
 **/

@Configuration
public class StreamConfig {
    @Bean
    public Consumer<KStream<String, Order>> processOrder() {
        return input -> {
            input.foreach((key, order) -> System.out.println("Order - Key: " + key + " Order: " + order));
            KStream<String, Order>[] branches = input.branch(
                    (key, order) -> order.getAmount() > 500,
                    (key, order) -> order.getAmount() <= 500
            );
            branches[0].to("high-value-topic");
            branches[1].to("normal-topic");
        };
    }

}