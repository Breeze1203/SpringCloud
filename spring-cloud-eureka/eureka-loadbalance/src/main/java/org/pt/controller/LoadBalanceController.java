package org.pt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class LoadBalanceController {

    private final DiscoveryClient discoveryClient;
    private final LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;


    @Autowired
    public LoadBalanceController(DiscoveryClient discoveryClient, LoadBalancerClient loadBalancerClient,RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate=restTemplate;
    }

    // 使用LoadBalancerClient进行负载均衡
    @RequestMapping(value = "/getProvider_1", method = RequestMethod.GET)
    public String index() {
        // 获取所有服务名称
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }
        // 使用负载均衡选择一个服务实例
        ServiceInstance instance = loadBalancerClient.choose("provider"); // "provider" 是服务名称
        if (instance == null) {
            return "No available provider instance";
        }
        // 构建请求URL
        String url = String.format("http://%s:%s/getProvider", instance.getHost(), instance.getPort());
        System.out.println("Request URL: " + url);
        // 这里可以使用 RestTemplate 或其他 HTTP 客户端来发送请求
        // String response = restTemplate.getForObject(url, String.class);
        return "Request URL: " + url;
    }

    // 使用restTemplate进行负载均衡
    @RequestMapping(value = "/getProvider_2", method = RequestMethod.GET)
    public String doOtherStuff() {
        return restTemplate.getForObject("http://provider/getProvider", String.class);
    }
}