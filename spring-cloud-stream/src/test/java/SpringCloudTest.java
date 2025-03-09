import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.pt.entity.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Properties;
import java.util.Random;

/**
 * @ClassName SpringCloudTest
 * @Author pt
 * @Description
 * @Date 2025/3/7 16:31
 **/
@SpringBootTest(classes = org.pt.KafkaStreamApplication.class)
@DirtiesContext
public class SpringCloudTest {

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
        for (int i = 1; i <= 10; i++) {
            kafkaProducer.send(new
                    ProducerRecord<>("string", i % 2, "string", "分区" + i % 2 + "上传数据:" + i));
        }
        kafkaProducer.close();
    }

    // 模拟数组类型的消息处理
    @Test
    public void testProcessArray() throws InterruptedException {

    }

    // 模拟流类型的消息处理
    @Test
    public void testProcessStream() throws InterruptedException {

    }

    @Test
    public void testProcessOrderWithConsumer() throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        KafkaProducer<String, Order> producer = new KafkaProducer<>(properties);
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            producer.send(new
                    ProducerRecord<>("order", 0, "order", new Order((long) i, "customerId" + i, "product" + i, 400 + (random.nextDouble() * (600 - 400)))));
        }
        producer.close();
    }
}
