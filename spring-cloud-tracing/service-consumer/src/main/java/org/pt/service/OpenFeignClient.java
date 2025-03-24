package org.pt.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/*
@RequestMapping...这些注解两套使用逻辑，在feign里面，是将参数绑定到请求路径，在controller里面是将
请求的参数解析过来
 */
@FeignClient(name = "consumer",url = "http://localhost:6666", fallback = Fallback.class)
public interface OpenFeignClient {
    @GetMapping("/consumer")
    String getConsumer();
}

@Component
class Fallback implements OpenFeignClient {


    @Override
    public String getConsumer() {
        return "网络错误请稍后再试";
    }
}
