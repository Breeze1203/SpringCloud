package org.pt.config;

import feign.InvocationContext;
import feign.ResponseInterceptor;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomResponseInterceptor
 * @Author pt
 * @Description
 * @Date 2025/2/19 15:51
 **/

public class CustomResponseInterceptor implements ResponseInterceptor {
    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
        return null;
    }

    @Override
    public ResponseInterceptor andThen(ResponseInterceptor nextInterceptor) {
        return ResponseInterceptor.super.andThen(nextInterceptor);
    }

    @Override
    public Chain apply(Chain chain) {
        return ResponseInterceptor.super.apply(chain);
    }
}
