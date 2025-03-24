package org.pt.controller;

import org.pt.resp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/loadbanlance")
public class LoadBalanceController {

    private final DiscoveryClient discoveryClient;
    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate roundRestTemplate;  // 用于轮询策略
    private final RestTemplate randomRestTemplate; // 用于随机策略

    @Autowired
    public LoadBalanceController(
            DiscoveryClient discoveryClient,
            LoadBalancerClient loadBalancerClient,
            @Qualifier("round") RestTemplate roundRestTemplate,   // 使用 @Qualifier 指定 round Bean
            @Qualifier("random") RestTemplate randomRestTemplate  // 使用 @Qualifier 指定 random Bean
    ) {
        this.discoveryClient = discoveryClient;
        this.loadBalancerClient = loadBalancerClient;
        this.roundRestTemplate = roundRestTemplate;
        this.randomRestTemplate = randomRestTemplate;
    }

    // 使用 LoadBalancerClient 进行负载均衡
    @RequestMapping(value = "/getProvider", method = RequestMethod.GET)
    public String index() {
        // 获取所有服务名称
        List<String> services = discoveryClient.getServices();
        // 使用负载均衡选择一个服务实例
        ServiceInstance instance = loadBalancerClient.choose("provider"); // "provider" 是服务名称
        if (instance == null) {
            return "No available provider instance";
        }
        // 构建请求 URL
        String url = String.format("http://%s:%s/getProvider", instance.getHost(), instance.getPort());
        System.out.println("Request URL: " + url);
        return "Request URL: " + url;
    }

    // 使用 roundRestTemplate（轮询策略）进行负载均衡
    @RequestMapping(value = "/getProvider/round", method = RequestMethod.GET)
    public Response doRoundStuff() {
        return roundRestTemplate.getForObject("http://provider-round/getProvider",Response.class);
    }

    // 使用 randomRestTemplate（随机策略）进行负载均衡
    @RequestMapping(value = "/getProvider/random", method = RequestMethod.GET)
    public Response doRandomStuff() {
        return randomRestTemplate.getForObject("http://provider-random/getProvider", Response.class);
    }
}