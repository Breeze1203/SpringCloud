package org.pt.controller;

/**
 * @ClassName KafkaConfigController
 * @Author pt
 * @Description
 * @Date 2025/3/19 16:52
 **/

import org.pt.entity.KafkaBindingInfo;
import org.pt.resp.Response;
import org.pt.service.KafkaConfigService;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stream")
public class KafkaConfigController {
    private KafkaConfigService kafkaService;


    public KafkaConfigController(KafkaConfigService kafkaService) {
        this.kafkaService = kafkaService;
    }


    @GetMapping("/all")
    public Response<Map<String, BindingProperties>> getKafkaConfig() {
        return new Response<Map<String, BindingProperties>>(200,kafkaService.getKafkaConfigAll());
    }
}
