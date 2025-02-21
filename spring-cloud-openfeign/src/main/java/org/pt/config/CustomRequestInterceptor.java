package org.pt.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomRequestInterceptor
 * @Author pt
 * @Description
 * @Date 2025/2/19 15:50
 **/

public class CustomRequestInterceptor implements RequestInterceptor {
    /*
    封装了请求参数
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {

    }
}
