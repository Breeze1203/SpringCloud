package org.pt.config;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.pt.entity.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName StreamConfig
 * @Author pt
 * @Description
 * @Date 2025/3/6 16:31
 **/

@Configuration
public class StreamConfig {
    // String 消费者
    @Bean
    public Consumer<KStream<String, String>> processString() {
        return input -> {
            // foreach: 遍历 KStream 中的每条记录，打印键和值
            // 参数 key: 消息的键 (String 类型)
            // 参数 value: 消息的值 (String 类型)
            System.out.println("------------------start--------------------------");
            input.foreach((key, value) -> System.out.println("String - Key: " + key + " Value: " + value));
            System.out.println("------------------end--------------------------");
            // branch: 根据条件将输入流拆分为多个子流
            // 返回值: KStream 数组，每个元素是一个子流
            // 第一个条件: value.contains("0") - 如果消息包含 "urgent"，进入 branches[0]
            // 第二个条件: value.contains("1") - 所有消息都进入 branches[1]（作为默认分支）
            KStream<String, String>[] branches = input.branch(
                    (key, value) -> value.contains("0"),
                    (key, value) -> value.contains("1")
            );
            // to: 将子流发送到指定的 Kafka 主题
            // branches[0]: 发送到 "urgent-string-topic"，处理紧急消息
            branches[0].to("zero-string-topic");
            // branches[1]: 发送到 "normal-string-topic"，处理普通消息
            branches[1].to("one-string-topic");
        };
    }

    // 数组消费者
    @Bean
    public Consumer<KStream<String, List<String>>> processArray() {
        return input -> {
            // foreach: 遍历 KStream 中的每条记录，打印键和数组值
            // 参数 key: 消息的键 (String 类型)
            // 参数 value: 消息的值 (List<String> 类型，表示字符串数组)
            input.foreach((key, value) -> System.out.println("Array - Key: " + key + " Value: " + value));
            // branch: 根据数组长度将输入流拆分为多个子流
            // 返回值: KStream 数组，每个元素是一个子流
            // 第一个条件: value.size() > 3 - 如果数组长度大于 3，进入 branches[0]
            // 第二个条件: true - 所有消息都进入 branches[1]（作为默认分支）
            KStream<String, List<String>>[] branches = input.branch(
                    (key, value) -> value.size() > 3,
                    (key, value) -> true
            );
            // to: 将子流发送到指定的 Kafka 主题
            // branches[0]: 发送到 "large-array-topic"，处理大数组
            branches[0].to("large-array-topic");
            // branches[1]: 发送到 "small-array-topic"，处理小数组或普通数组
            branches[1].to("small-array-topic");
        };
    }

    // 流消费者
    @Bean
    public Consumer<KStream<String, String>> processStream() {
        return input -> {
            // foreach: 遍历 KStream 中的每条记录，打印键和值
            // 参数 key: 消息的键 (String 类型)
            // 参数 value: 消息的值 (String 类型)
            input.foreach((key, value) -> System.out.println("Stream - Key: " + key + " Value: " + value));
            // groupByKey: 按键对消息分组，为后续聚合操作准备
            // windowedBy: 定义时间窗口，这里是每分钟一个窗口，无宽限期
            // TimeWindows.ofSizeWithNoGrace: 创建固定大小的时间窗口（1分钟）
            // count: 计算每个窗口内每组消息的数量
            // toStream: 将聚合结果转换回 KStream
            // to: 将聚合结果发送到 "stream-stats-topic"
            input.groupByKey()
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
                    .count()
                    .toStream()
                    .to("stream-stats-topic");
        };
    }

    // Order 消费者
    @Bean
    public Consumer<KStream<String, Order>> processOrder() {
        return input -> {
            // foreach: 遍历 KStream 中的每条记录，打印键和 Order 对象
            // 参数 key: 消息的键 (String 类型)
            // 参数 order: 消息的值 (Order 类型，自定义实体类)
            input.foreach((key, order) -> System.out.println("Order - Key: " + key + " Order: " + order));
            // branch: 根据订单金额将输入流拆分为多个子流
            // 返回值: KStream 数组，每个元素是一个子流
            // 第一个条件: order.getAmount() > 500 - 如果金额大于 500，进入 branches[0]
            // 第二个条件: true - 所有消息都进入 branches[1]（作为默认分支）
            KStream<String, Order>[] branches = input.branch(
                    (key, order) -> order.getAmount() > 500,
                    (key, order) -> order.getAmount() < 500
            );
            // to: 将子流发送到指定的 Kafka 主题
            // branches[0]: 发送到 "high-value-topic"，处理高金额订单
            branches[0].to("high-value-topic");
            // branches[1]: 发送到 "normal-topic"，处理普通订单
            branches[1].to("normal-topic");
        };
    }

}