<h5 align="center">
  <img alt="SpringBoot" src="https://img.shields.io/badge/-SpringBoot--3.4.X-007396?style=flat-square&logo=SpringBoot&logoColor=white" />
  <img alt="SpringCloud" src="https://img.shields.io/badge/-SpringCloud--latest-00ADD8?style=flat-square&logo=SpringCloud&logoColor=white" />
  <img alt="JAVA" src="https://img.shields.io/badge/-JDK--17-4479A1?style=flat-square&logo=JAVA&logoColor=white" />
</h5>

Spring Cloud                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 项目根目录<br>
|--- boo                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# spring-cloud-config 配置文件夹<br>
|--- bus                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# spring-cloud-bus 配置文件夹<br>
|--- spring-cloud-alibaba             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# spring-cloud-alibaba 各组件使用（待完善）<br>
|--- spring-cloud-bus            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 消息总线<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---bus-service-a          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# a 服务，config-server 总配置，配置更新通知 b、c 服务<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---bus-service-b          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# b 服务<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---bus-service-c          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# c 服务<br>
|--- spring-cloud-circuitbreaker     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 断路器，替代 Hystrix<br>
|---spring-cloud-config             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 分布式配置中心组件<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---config-client              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 配置客户端<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---config-server              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 配置服务端<br>
|---spring-cloud-eureka             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 注册中心组件<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---eureka-consumer            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 消费者<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---eureka-loadbalance         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 负载均衡，替代 Ribbon<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---eureka-provider            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 服务提供者<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---eureka-server              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 注册中心<br>
|---spring-cloud-gateway            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 网关<br>
|--- spring-cloud-openfeign          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 远程调用<br>
|--- spring-cloud-stream             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 消息驱动（待完善）<br>
|---spring-cloud-tracing            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 服务跟踪（替代 Sleuth，选用 micrometer+zipkin）<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---service-consumer                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 服务消费者<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---service-provider                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# 服务提供者<br>




