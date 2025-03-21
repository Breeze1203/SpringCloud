package org.pt.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomizeSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("--------------------授权成功----------------");
        System.out.println("登录成功，用户: " + authentication.getName());

        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 构造响应数据
        Map<String, Object> responseData = new HashMap<>();
        Map<String, Object> userInfo = new HashMap<>();

        // 添加用户信息
        userInfo.put("username", authentication.getName());
        userInfo.put("authorities", authentication.getAuthorities());
        // 添加到响应数据
        responseData.put("code", 200);
        responseData.put("message", "success");
        responseData.put("data", userInfo);
        responseData.put("timestamp", System.currentTimeMillis());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 添加对 Java 8 时间类型的支持
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 可选：格式化日期为 ISO 字符串
        //PrintWriter writer = resp.getWriter();

        try {
            // 先转换为 JSON 字符串，然后打印
            String jsonResponse = objectMapper.writeValueAsString(responseData);
            String redirectUri = "http://localhost:3000/callback?response=" + jsonResponse;
            response.sendRedirect(redirectUri);
            //writer.print(jsonResponse);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "服务器内部错误");
            errorResponse.put("timestamp", System.currentTimeMillis());
            String errorJson = objectMapper.writeValueAsString(errorResponse);
            String redirectUri = "http://localhost:3000/callback?response=" + errorJson;
            response.sendRedirect(redirectUri);
            //writer.print(errorJson);
        } finally {
            //writer.flush();
            //writer.close();
        }
    }
}