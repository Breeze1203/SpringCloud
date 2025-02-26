package org.pt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ConsumerController
 * @Author pt
 * @Description
 * @Date 2025/2/26 16:34
 **/
@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @GetMapping("/consumer")
    public String consumer() {
        logger.info("consumer Service: Processing consume");
        return "consumer Processed!";
    }
}
