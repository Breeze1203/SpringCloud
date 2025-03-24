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

    // Eureka 配置
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServiceUrl;

    @Value("${eureka.instance.prefer-ip-address}")
    private boolean preferIpAddress;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    // MySQL 配置
    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriverClassName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    // Redis 配置
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    // MongoDB 配置
    @Value("${spring.data.mongodb.host}")
    private String mongodbHost;

    @Value("${spring.data.mongodb.port}")
    private int mongodbPort;

    @Value("${spring.data.mongodb.database}")
    private String mongodbDatabase;

    @Value("${spring.data.mongodb.username}")
    private String mongodbUsername;

    @Value("${spring.data.mongodb.password}")
    private String mongodbPassword;

    // Getter 和 Setter 方法
    public String getEurekaServiceUrl() {
        return eurekaServiceUrl;
    }

    public void setEurekaServiceUrl(String eurekaServiceUrl) {
        this.eurekaServiceUrl = eurekaServiceUrl;
    }

    public boolean isPreferIpAddress() {
        return preferIpAddress;
    }

    public void setPreferIpAddress(boolean preferIpAddress) {
        this.preferIpAddress = preferIpAddress;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public void setDatasourceUsername(String datasourceUsername) {
        this.datasourceUsername = datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public void setDatasourcePassword(String datasourcePassword) {
        this.datasourcePassword = datasourcePassword;
    }

    public String getDatasourceDriverClassName() {
        return datasourceDriverClassName;
    }

    public void setDatasourceDriverClassName(String datasourceDriverClassName) {
        this.datasourceDriverClassName = datasourceDriverClassName;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public String getMongodbHost() {
        return mongodbHost;
    }

    public void setMongodbHost(String mongodbHost) {
        this.mongodbHost = mongodbHost;
    }

    public int getMongodbPort() {
        return mongodbPort;
    }

    public void setMongodbPort(int mongodbPort) {
        this.mongodbPort = mongodbPort;
    }

    public String getMongodbDatabase() {
        return mongodbDatabase;
    }

    public void setMongodbDatabase(String mongodbDatabase) {
        this.mongodbDatabase = mongodbDatabase;
    }

    public String getMongodbUsername() {
        return mongodbUsername;
    }

    public void setMongodbUsername(String mongodbUsername) {
        this.mongodbUsername = mongodbUsername;
    }

    public String getMongodbPassword() {
        return mongodbPassword;
    }

    public void setMongodbPassword(String mongodbPassword) {
        this.mongodbPassword = mongodbPassword;
    }

    // toString 方法（可选，便于调试）
    @Override
    public String toString() {
        return "ServiceConfig{" +
                "eurekaServiceUrl='" + eurekaServiceUrl + '\'' +
                ", preferIpAddress=" + preferIpAddress +
                ", instanceId='" + instanceId + '\'' +
                ", datasourceUsername='" + datasourceUsername + '\'' +
                ", datasourcePassword='" + datasourcePassword + '\'' +
                ", datasourceDriverClassName='" + datasourceDriverClassName + '\'' +
                ", datasourceUrl='" + datasourceUrl + '\'' +
                ", redisHost='" + redisHost + '\'' +
                ", redisPort=" + redisPort +
                ", redisPassword='" + redisPassword + '\'' +
                ", mongodbHost='" + mongodbHost + '\'' +
                ", mongodbPort=" + mongodbPort +
                ", mongodbDatabase='" + mongodbDatabase + '\'' +
                ", mongodbUsername='" + mongodbUsername + '\'' +
                ", mongodbPassword='" + mongodbPassword + '\'' +
                '}';
    }
}