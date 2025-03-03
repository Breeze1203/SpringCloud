package org.pt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SuccessController
 * @Author pt
 * @Description
 * @Date 2025/3/1 18:28
 **/
@RestController
public class SuccessController {

    @GetMapping("/success")
    private String LoginSuccess(){
        return "login success";
    }

}
