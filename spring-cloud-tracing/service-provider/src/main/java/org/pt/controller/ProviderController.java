package org.pt.controller;

import org.pt.service.OpenFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName ProviderController
 * @Author pt
 * @Description
 * @Date 2025/2/26 16:34
 **/
@RestController
public class ProviderController {
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OpenFeignClient openFeignClient;

    @GetMapping("/provider")
    public String provider() {
        logger.info("Provider Service: Creating order");
        String paymentResponse = restTemplate.getForObject("http://localhost:6666/consumer", String.class);
        logger.info("Provider Service: Payment response received");
        return "Order Created -> " + paymentResponse;
    }

    @GetMapping("/openFeign")
    public String openFeign() {
        String Response = openFeignClient.getConsumer();
        logger.info("Provider Service: Payment response received");
        return "Order Created -> " + Response;
    }
}
