import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.pt.entity.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName SpringCloudTest
 * @Author pt
 * @Description
 * @Date 2025/3/7 16:31
 **/
@SpringBootTest(classes = org.pt.KafkaStreamApplication.class)
@DirtiesContext
public class SpringCloudTest {

    private static void topic(String topic, int partition) {
        // 配置 AdminClient
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        try (AdminClient admin = AdminClient.create(props)) {
            // 检查主题是否已存在
            ListTopicsResult listTopicsResult = admin.listTopics();
            Map<String, TopicDescription> existingTopics = admin.describeTopics(Collections.singleton(topic)).all().get();
            if (existingTopics.containsKey(topic)) {
                // 主题已存在，获取当前分区数
                TopicDescription topicDescription = existingTopics.get(topic);
                int currentPartitions = topicDescription.partitions().size();
                System.out.println("主题 " + topic + " 已存在，当前分区数: " + currentPartitions);
                if (currentPartitions < partition) {
                    // 如果目标分区数大于当前分区数，增加分区
                    NewPartitions newPartitions = NewPartitions.increaseTo(partition);
                    Map<String, NewPartitions> partitionsMap = Collections.singletonMap(topic, newPartitions);
                    CreatePartitionsResult partitionsResult = admin.createPartitions(partitionsMap);
                    partitionsResult.all().get(); // 等待分区调整完成
                    System.out.println("主题 " + topic + " 分区数已调整为: " + partition);
                } else if (currentPartitions > partition) {
                    // Kafka 不支持减少分区，提示用户
                    System.out.println("无法将分区数从 " + currentPartitions + " 减少到 " + partition + "，Kafka 不支持减少分区。");
                } else {
                    System.out.println("主题 " + topic + " 的分区数已是 " + partition + "，无需修改。");
                }
            } else {
                // 主题不存在，创建新主题
                short replicationFactor = 1; // 副本数
                NewTopic newTopic = new NewTopic(topic, partition, replicationFactor);
                CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
                result.all().get(); // 等待创建完成
                System.out.println("主题 " + topic + " 创建成功，分区数: " + partition);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("操作失败: " + e.getMessage());
        }
    }

    @Test
    public void AdminClient(){
        topic("string",2);
    }

    // 模拟 String 类型的消息处理
    @Test
    public void testProcessString() throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<String, String>(properties);
        // 4. 调用 send方法,发送消息
        for (int i = 1; i <= 100; i++) {
            kafkaProducer.send(new
                    ProducerRecord<>("string", i % 2, "string", "分区" + i % 2 + "上传数据:" + i));
        }
        kafkaProducer.close();
    }


    @Test
    public void testProcessOrderWithConsumer() throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        KafkaProducer<String, Order> producer = new KafkaProducer<>(properties);
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            // 生成订单数据
            Long id = (long) (i % 2);
            String customerId = "customerId" + i;
            String product = "product" + i;
            Double amount = 400 + (random.nextDouble() * (600 - 400));
            Order order = new Order(id, customerId, product, amount);
            // 发送消息
            ProducerRecord<String, Order> record = new ProducerRecord<>("order", 0, "order", order);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("Sent message to topic: " + metadata.topic() + ", partition: " + metadata.partition() + ", offset: " + metadata.offset());
                } else {
                    System.err.println("Failed to send message: " + exception.getMessage());
                }
            });
            // 添加短暂延迟以确保消息被发送
            Thread.sleep(100);
        }
        // 确保所有消息发送完成
        producer.flush();
        producer.close();
        // 等待一段时间以确保消息被处理
        Thread.sleep(5000);
    }
}
