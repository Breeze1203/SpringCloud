package org.pt.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName InstanceService
 * @Author pt
 * @Description
 * @Date 2025/3/18 10:18
 **/
@Service(value = "InstanceService")
public class InstanceService {
    @Autowired
    // 直接注入 Eureka Server 的注册表
    private PeerAwareInstanceRegistry instanceRegistry;

    public List<ServiceInstanceInfo> getAllInstances() {
        // 从注册表中获取所有应用和服务实例
        List<ServiceInstanceInfo> instanceInfos = instanceRegistry.getApplications().getRegisteredApplications().stream()
                .flatMap(application -> application.getInstances().stream())
                .map(instanceInfo -> new ServiceInstanceInfo(
                        instanceInfo.getStatus(),
                        instanceInfo.getAppName(),               // 服务名称
                        instanceInfo.getHostName(),             // 主机地址
                        instanceInfo.getPort(),                 // 端口
                        URI.create(instanceInfo.getHomePageUrl()), // URI
                        instanceInfo.getInstanceId(),           // 实例 ID
                        instanceInfo.getVIPAddress() != null && instanceInfo.getVIPAddress().startsWith("https"), // 是否安全连接
                        instanceInfo.getMetadata()              // 元数据
                ))
                .collect(Collectors.toList());
        return instanceInfos;
    }

    public String getProviderStatus() {
        // 获取所有注册的应用
        List<InstanceInfo> providerInstances = instanceRegistry.getApplications()
                .getRegisteredApplications().stream()
                .filter(application -> "provider".equalsIgnoreCase(application.getName())) // 筛选 provider 服务
                .flatMap(application -> application.getInstances().stream()) // 获取所有实例
                .collect(Collectors.toList());

        // 如果没有找到 provider 实例，返回 UNKNOWN
        if (providerInstances.isEmpty()) {
            return "UNKNOWN";
        }
        // 检查所有实例的状态
        boolean allUp = providerInstances.stream()
                .allMatch(instance -> instance.getStatus() == InstanceInfo.InstanceStatus.UP);
        boolean allDown = providerInstances.stream()
                .allMatch(instance -> instance.getStatus() == InstanceInfo.InstanceStatus.DOWN);
        // 根据实例状态返回结果
        if (allUp) {
            return "UP";
        } else if (allDown) {
            return "DOWN";
        } else {
            // 如果部分实例 UP，部分 DOWN，返回混合状态（可根据需求调整）
            return "PARTIAL";
        }
    }
    public String stopProvider() {
        List<InstanceInfo> providerInstances = instanceRegistry.getApplications().getRegisteredApplications().stream()
                .filter(application -> "provider".equalsIgnoreCase(application.getName())) // 筛选 provider 服务
                .flatMap(application -> application.getInstances().stream()) // 获取所有实例
                .collect(Collectors.toList());
        if (providerInstances.isEmpty()) {
            return "No provider instances found.";
        }

        boolean allStopped = true;
        for (InstanceInfo instanceInfo : providerInstances) {
            try {
                instanceRegistry.statusUpdate(
                        instanceInfo.getAppName(),
                        instanceInfo.getInstanceId(),
                        InstanceInfo.InstanceStatus.DOWN,
                        String.valueOf(instanceInfo.getLastDirtyTimestamp()),
                        false
                );
            } catch (Exception e) {
                allStopped = false;
            }
        }

        return allStopped ? "All provider instances stopped successfully." : "Some provider instances failed to stop.";
    }

    public String startProvider() {
        instanceRegistry.getApplications().getRegisteredApplications().stream()
                .filter(application -> "provider".equalsIgnoreCase(application.getName()))
                .flatMap(application -> application.getInstances().stream())
                .forEach(instanceInfo -> {
                    instanceRegistry.statusUpdate(
                            instanceInfo.getAppName(),
                            instanceInfo.getInstanceId(),
                            InstanceInfo.InstanceStatus.UP,
                            String.valueOf(instanceInfo.getLastDirtyTimestamp()),
                            false
                    );
                });
        return"All provider instances restart successfully.";
    }

    // 内部类用于封装返回的信息
    public static class ServiceInstanceInfo {
        private InstanceInfo.InstanceStatus status;
        private String serviceId;
        private String host;
        private int port;
        private String uri;
        private String instanceId;
        private boolean secure;
        private Map<String, String> metadata;

        public ServiceInstanceInfo(InstanceInfo.InstanceStatus status, String serviceId, String host, int port,
                                   URI uri, String instanceId, boolean secure,
                                   Map<String, String> metadata) {
            this.status = status;
            this.serviceId = serviceId;
            this.host = host;
            this.port = port;
            this.uri = uri != null ? uri.toString() : null;
            this.instanceId = instanceId;
            this.secure = secure;
            this.metadata = metadata;
        }

        public String getServiceId() { return serviceId; }
        public String getHost() { return host; }
        public int getPort() { return port; }
        public String getUri() { return uri; }
        public String getInstanceId() { return instanceId; }
        public boolean isSecure() { return secure; }
        public Map<String, String> getMetadata() { return metadata; }

        public InstanceInfo.InstanceStatus getStatus() {
            return status;
        }
    }
}
