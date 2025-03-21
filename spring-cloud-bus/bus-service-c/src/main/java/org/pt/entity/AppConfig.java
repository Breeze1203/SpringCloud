package org.pt.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName AppConfig
 * @Author pt
 * @Description
 * @Date 2025/3/21 10:52
 * 项目配置文件
 **/

@Component
public class AppConfig {

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServiceUrl;

    @Value("${eureka.instance.prefer-ip-address}")
    private boolean preferIpAddress;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriverClassName;

    @Value("${profile}")
    private String profile;

    // Getters（用于访问这些字段）
    public String getEurekaServiceUrl() {
        return eurekaServiceUrl;
    }

    public boolean isPreferIpAddress() {
        return preferIpAddress;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public String getDatasourceDriverClassName() {
        return datasourceDriverClassName;
    }

    public String getProfile() {
        return profile;
    }

    // 可选：toString 方法，便于调试
    @Override
    public String toString() {
        return "AppConfig{" +
                "eurekaServiceUrl='" + eurekaServiceUrl + '\'' +
                ", preferIpAddress=" + preferIpAddress +
                ", instanceId='" + instanceId + '\'' +
                ", datasourceUsername='" + datasourceUsername + '\'' +
                ", datasourcePassword='" + datasourcePassword + '\'' +
                ", datasourceDriverClassName='" + datasourceDriverClassName + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
