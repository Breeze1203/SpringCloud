import com.netflix.discovery.EurekaClientConfig;
import org.junit.jupiter.api.Test;
import org.pt.ConfigClientApplication; // 确保包路径正确
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.core.env.Environment;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * @ClassName EurekaConfigTest
 * @Author pt
 * @Description 测试 Eureka 配置加载
 * @Date 2025/2/22 20:05
 **/
@SpringBootTest(classes = ConfigClientApplication.class)
public class EurekaConfigTest {

    @Autowired
    private Environment environment;

    @Autowired
    private EurekaClientConfig eurekaClientConfig;

    @Autowired
    private EurekaInstanceConfigBean eurekaInstanceConfig;

    @Test
    public void testEurekaClientConfig() {
        System.out.println("=== 开始测试 Eureka Client 配置 ===");

        assertNotNull("EurekaClientConfig should not be null", eurekaClientConfig);
        System.out.println("EurekaClientConfig 注入成功");

        // 测试 eureka.client.service-url.defaultZone
        String defaultZone = eurekaClientConfig.getEurekaServerServiceUrls("defaultZone").get(0);
        String expectedDefaultZone = "http://localhost:8080/eureka/";

        System.out.println("预期 Eureka 服务 URL: " + expectedDefaultZone);
        System.out.println("实际 Eureka 服务 URL: " + defaultZone);

        assertEquals("Eureka service URL should be 'http://localhost:8080/eureka/'",
                expectedDefaultZone, defaultZone);
        System.out.println("Eureka 服务 URL 测试通过 ✓");
        System.out.println("=== Eureka Client 配置测试完成 ===\n");
    }

    @Test
    public void testEurekaInstanceConfig() {
        System.out.println("=== 开始测试 Eureka Instance 配置 ===");

        assertNotNull("EurekaInstanceConfigBean should not be null", eurekaInstanceConfig);
        assertNotNull("Environment should not be null", environment);
        System.out.println("EurekaInstanceConfigBean 和 Environment 注入成功");

        // 测试 eureka.instance.prefer-ip-address
        boolean preferIpAddress = eurekaInstanceConfig.isPreferIpAddress();
        System.out.println("预期 prefer-ip-address: true");
        System.out.println("实际 prefer-ip-address: " + preferIpAddress);

        assertTrue("prefer-ip-address should be true", preferIpAddress);
        System.out.println("prefer-ip-address 测试通过 ✓");

        // 测试 spring.application.name
        String appName = environment.getProperty("spring.application.name");
        String expectedAppName = "dispace";

        System.out.println("预期应用名: " + expectedAppName);
        System.out.println("实际应用名: " + appName);

        assertEquals("Application name should be 'dispace'", expectedAppName, appName);
        System.out.println("应用名测试通过 ✓");
        System.out.println("=== Eureka Instance 配置测试完成 ===\n");
    }

    @Test
    public void testInstanceIdConfig() {
        System.out.println("=== 开始测试 Eureka Instance ID 配置 ===");

        assertNotNull("EurekaInstanceConfigBean should not be null", eurekaInstanceConfig);
        assertNotNull("Environment should not be null", environment);
        System.out.println("EurekaInstanceConfigBean 和 Environment 注入成功");

        // 测试 eureka.instance.instance-id
        String instanceId = eurekaInstanceConfig.getInstanceId();
        String ipAddress = environment.getProperty("spring.cloud.client.ip-address");
        String port = environment.getProperty("server.port");

        System.out.println("实际 IP 地址: " + ipAddress);
        System.out.println("实际端口: " + port);

        if (ipAddress == null || port == null) {
            System.out.println("警告: IP 地址或端口未配置!");
        }

        String expectedInstanceId = ipAddress + ":" + port;
        System.out.println("预期 Instance ID: " + expectedInstanceId);
        System.out.println("实际 Instance ID: " + instanceId);

        assertEquals("Instance ID should be in format {ip-address}:{port}",
                expectedInstanceId, instanceId);
        System.out.println("Instance ID 测试通过 ✓");
        System.out.println("=== Eureka Instance ID 配置测试完成 ===\n");
    }
}