package org.pt.controller;

import org.pt.entity.AppConfig;
import org.pt.resp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AppConfigController
 * @Author pt
 * @Description
 * @Date 2025/3/21 10:55
 **/
@RestController
@RequestMapping("/bus-c")
public class AppConfigController {

    private final AppConfig appConfig;

    @Autowired
    public AppConfigController(AppConfig appConfig) {
        this.appConfig = appConfig; // Spring 注入已初始化的 AppConfig
    }

    @GetMapping("/getConfig")
    public Response<AppConfig> getAppConfig() {
        return new Response<>(200, appConfig); // 返回注入的实例
    }
}