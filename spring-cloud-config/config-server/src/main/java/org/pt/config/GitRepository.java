package org.pt.config;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @ClassName GitRepository
 * @Author pt
 * @Description 自定义环境Git存储库
 * @Date 2025/2/10 11:33
 **/
public class GitRepository implements EnvironmentRepository, Ordered {

    @Override
    public Environment findOne(String application, String profile, String label) {
        // Simulate fetching configuration from a custom source
        final Map<String, String> properties = Map.of(
                "key1", "value1",
                "key2", "value2",
                "key3", "value3"
        );
        Environment environment = new Environment(application, profile);
        environment.add(new PropertySource("GitSource", properties));
        return environment;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
