package org.pt.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName RouteController
 * @Author pt
 * @Description
 * @Date 2025/2/20 11:34
 **/
@RestController
public class RouteController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    @GetMapping("/method_route")
    public String methodRoute(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the method_route method.";
    }

    @GetMapping("/path_route/{id}")
    public String pathRoute(HttpServletRequest request,@PathVariable String id) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "Path ID: " + id;
    }

    @GetMapping("/query_route")
    public String queryRoute(HttpServletRequest request,@RequestParam String param) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "Query Parameter: " + param;
    }

    @GetMapping("/weight_high")
    public String weightHigh(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the weight_high method.";
    }
}
