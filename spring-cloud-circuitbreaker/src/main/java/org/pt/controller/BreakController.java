package org.pt.controller;

import org.pt.resp.Response;
import org.pt.service.BreakService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BreakController
 * @Author pt
 * @Description
 * @Date 2025/2/19 09:37
 **/
@RestController
@RequestMapping("/break")
public class BreakController {
    private final BreakService breakService;


    public BreakController(BreakService breakService) {
        this.breakService = breakService;
    }

    @GetMapping("/getSlow")
    public Response slow(){
        return breakService.slow();
    }

    @GetMapping("/getSlowTwo")
    public Response slow_two(){
        return breakService.slow_two();
    }
}
