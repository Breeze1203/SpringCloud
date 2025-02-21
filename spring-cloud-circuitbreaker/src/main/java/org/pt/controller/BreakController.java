package org.pt.controller;

import org.pt.service.BreakService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @ClassName BreakController
 * @Author pt
 * @Description
 * @Date 2025/2/19 09:37
 **/
@RestController
public class BreakController {
    private final BreakService breakService;


    public BreakController(BreakService breakService) {
        this.breakService = breakService;
    }

    @GetMapping("/getSlow")
    public String slow(){
        return breakService.slow();
    }

    @GetMapping("/getSlowTwo")
    public String slow_two(){
        return breakService.slow_two();
    }
    @GetMapping("/getSlowThree")
    public Mono<String> slow_three(){
        return breakService.slow_three();
    }
}
