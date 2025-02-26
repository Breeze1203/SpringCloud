<h5>SpringCloud最新版</h5>

Spring Cloud                          # 项目根目录
├── boo                               # spring-cloud-config配置文件夹
├── bus                               # spring-cloud-bus配置文件夹
├── spring-cloud-alibaba              # spring-cloud-alibaba各组件使用（待完善）
│   ├── spring-cloud-bus                   # 消息总线
│   │   ├── bus-service-a                  # a服务，config-server总配置，配置更新通知b、c服务
│   │   └── bus-service-b                  # b服务
│   └── bus-service-c                      # c服务
├── spring-cloud-circuitbreaker        # 断路器，替代 Hystrix
├── spring-cloud-config                # 分布式配置中心组件
│   ├── config-client
│   └── config-server
├── spring-cloud-eureka                # 注册中心组件
│   ├── eureka-consumer                # 消费者
│   ├── eureka-loadbalance             # 负载均衡 替代Ribbon
│   ├── eureka-provider                # 服务提供者
│   └── eureka-server                  # 注册中心
├── spring-cloud-gateway               # 网关
├── spring-cloud-openfeign             # 远程调用
├── spring-cloud-stream                # 消息驱动（待完善）
├── spring-cloud-tracing               # 服务跟踪（替代sleuth  选用micrometer+zipkin）
├── service-consumer
└── service-provider

---

