package org.pt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ConsumeController
 * @Author pt
 * @Description
 * @Date 2025/2/16 21:32
 **/
@RestController
public class ConsumeController {

    @RequestMapping(value = "/getProvider",method = RequestMethod.GET)
    public String index(){
        return "consumer";
    }

    // 获取所有商店信息
    @GetMapping("/stores")
    public List<String> getStores() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("consumer");
        strings.add("8084");
        return strings;
    }
}
