package org.pt.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RouteController
 * @Author pt
 * @Description
 * @Date 2025/2/20 10:25
 **/
@RestController
public class RouteController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    @GetMapping("/after_route")
    public String afterRoute(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the after_route method.";
    }

    @GetMapping("/before_route")
    public String beforeRoute(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the before_route method.";
    }

    @GetMapping("/between_route")
    public String betweenRoute(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the between_route method.";
    }

    @GetMapping("/cookie_route")
    public String cookieRoute(HttpServletRequest request,@CookieValue("session") String session) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "Cookie received: " + session;
    }

    @GetMapping("/header_route")
    public String headerRoute(HttpServletRequest request,@RequestHeader("Authorization") String auth) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "Authorization Header: " + auth;
    }

    @GetMapping("/host_route")
    public String hostRoute(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "Host: " + request.getServerName();
    }

    @GetMapping("/weight_high")
    public String weightHigh(HttpServletRequest request) {
        logger.info(request.getContextPath()+":"+request.getRequestURI());
        return "This is the weight_high method.";
    }

}
