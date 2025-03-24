package org.pt.service;

import org.pt.resp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

/**
 * @ClassName BreakService
 * @Author pt
 * @Description
 * @Date 2025/2/18 22:46
 **/
@Service
public class BreakService {
    @Value("${server.port}")
    private final String port;
    private final RestTemplate rest;
    private final CircuitBreakerFactory cbFactory;

    private final WebClient webClient;
    private final ReactiveCircuitBreaker readingListCircuitBreaker;

    public BreakService(@Value("${server.port}")String port, RestTemplate rest, CircuitBreakerFactory cbFactory, WebClient webClient, ReactiveCircuitBreakerFactory circuitBreakerFactory) {
        this.port = port;
        this.rest = rest;
        this.cbFactory = cbFactory;
        this.webClient=webClient;
        this.readingListCircuitBreaker = circuitBreakerFactory.create("recommended");
    }

    /*
    第一个参数是一个Lambda表达式() -> rest.getForObject("/slow", String.class)，表示正常情况下执行的代码。
    它通过rest对象发起一个GET请求到/slow路径，并尝试获取返回值（类型为String）。
    第二个参数是一个Lambda表达式throwable -> "fallback"，
    表示当第一个Lambda表达式执行失败时（例如请求超时或服务不可用），执行的备用代码。这里直接返回字符串"fallback"作为备用结果
     */
    public Response slow() {
        return cbFactory.create("slow").run(() -> rest.getForObject("http://provider-round/getProvider", Response.class), throwable -> new Response<>(200,"port:"+port));
    }


    public Response slow_two() {
        return cbFactory.create("slow_two").run(() -> rest.getForObject("http://provider-round/getProvider", Response.class), this::handleFallback_block);
    }

    public Response handleFallback_block(Throwable throwable) {
        return new Response<>(200,"port:"+port);
    }

    /*
    public Mono<String> slow_three() {
        //LOG.warn("Error making request to book service", throwable);
        return readingListCircuitBreaker.run(webClient.get().uri("http://provider/getProvider").retrieve().bodyToMono(String.class), this::handleFallback);
    }
     */

    public Mono<String> handleFallback(Throwable throwable) {
        if (throwable instanceof TimeoutException) {
            return Mono.just("Timeout occurred");
        } else if (throwable instanceof HttpClientErrorException) {
            return Mono.just("HTTP error occurred");
        } else {
            return Mono.just("port:"+port);
        }
    }
}
