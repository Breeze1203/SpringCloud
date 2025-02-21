package org.pt.config;

import io.netty.channel.ChannelOption;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import reactor.netty.http.client.HttpClient;

/**
 * @ClassName MyHttpClientCustomizer
 * @Author pt
 * @Description
 * @Date 2025/2/21 10:45
 **/
public class MyHttpClientCustomizer implements HttpClientCustomizer {

    @Override
    public HttpClient customize(HttpClient httpClient) {
        // Customize the HTTP client here
        return httpClient.tcpConfiguration(tcpClient -> {
            // Set the connect timeout to 5 seconds
            tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            // Set the read timeout to 10 seconds
            tcpClient.option(ChannelOption.SO_TIMEOUT, 10000);
            return tcpClient;
        });
    }
}
