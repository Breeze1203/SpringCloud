package org.pt.controller;

import org.pt.service.OpenFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName OpenFeignController
 * @Author pt
 * @Description
 * @Date 2025/2/19 14:13
 **/
@RestController
public class OpenFeignController {

    // 注入 OpenFeignClient 接口
    @Autowired
    private OpenFeignClient openFeignClient;

    // 调用 getStores 方法获取所有商店信息
    @GetMapping("/getAllStores")
    public List<String> getAllStores() {
        // 调用 OpenFeignClient 中的 getStores 方法
        List<String> stores = openFeignClient.getStores();
        System.out.println(stores);
        return stores;
    }

    // 调用 getStores 方法获取特定信息
    @GetMapping("/getStoreInfo")
    public int getStoreInfo(@RequestParam String get) {
        // 调用 OpenFeignClient 中的 getStores 方法
        return openFeignClient.getStore(get);
    }

    // 调用 update 方法更新商店信息
    @PutMapping("/updateStore/{storeId}")
    public int updateStore(@PathVariable("storeId") String storeId, @RequestParam String update) {
        // 调用 OpenFeignClient 中的 update 方法
        return openFeignClient.update(storeId, update);
    }

    // 调用 delete 方法删除商店
    @DeleteMapping("/deleteStore/{delId}")
    public void deleteStore(@PathVariable String delId) {
        // 调用 OpenFeignClient 中的 delete 方法
        openFeignClient.delete(delId);
    }
}
