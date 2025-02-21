package org.pt.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName ProducerConsumer
 * @Author pt
 * @Description
 * @Date 2025/2/16 21:50
 **/
@RestController
public class ProducerConsumer {

    private static Logger log = LoggerFactory.getLogger(ProducerConsumer.class);

    @RequestMapping(value = "/getProvider",method = RequestMethod.GET)
    public String index(){
        System.out.println("port:"+1111);
        return "consumer";
    }

    // 模拟数据
    private static final List<String> stores = new ArrayList<>();
    static {
        stores.add("Store A");
        stores.add("Store B");
        stores.add("Store C");
    }

    // 获取所有商店信息
    @GetMapping("/stores")
    public List<String> getStores() {
        log.info("获取所有商店信息");
        return stores;
    }

    // 获取特定信息
    @GetMapping("/getStoreById")
    public int getStores(@RequestParam String storeId) {
        System.out.println("获取特定信息");
        // 这里可以根据 get 参数返回不同的结果，这里简单返回一个示例值
        return stores.size();
    }

    // 更新商店信息
    @PostMapping("/stores/{storeId}")
    public int update(@PathVariable("storeId") String storeId, @RequestParam("update") String update) {
        System.out.println("更新");
        // 这里可以根据 storeId 和 update 参数进行更新操作，这里简单返回一个示例值
        return stores.size();
    }

    // 删除商店
    @DeleteMapping("/stores/{storeId}")
    public void delete(@PathVariable String storeId) {
        System.out.println("删除");
        // 这里可以根据 storeId 进行删除操作，这里简单模拟删除
        stores.remove(storeId);
    }
}
