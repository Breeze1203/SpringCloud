package org.pt.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/*
@RequestMapping...这些注解两套使用逻辑，在feign里面，是将参数绑定到请求路径，在controller里面是将
请求的参数解析过来
 */
@FeignClient(value ="provider",contextId ="provider",fallback = Fallback.class)
public interface OpenFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/stores")
    List<String> getStores();

    @GetMapping("/getStoreById")
    int getStore(String storeId);

    @PostMapping(value = "/stores/{storeId}")
    int update(@PathVariable("storeId") String storeId,@RequestParam("update") String update);

    @DeleteMapping("/stores/{storeId}")
    void delete(@PathVariable String deleteId);

}

@Component
class Fallback implements OpenFeignClient {


    @Override
    public List<String> getStores() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("e");
        return strings;
    }

    @Override
    public int getStore(String storeId) {
        return 0;
    }

    @Override
    public int update(String storeId, String update) {
        return 0;
    }

    @Override
    public void delete(String deleteId) {

    }
}
