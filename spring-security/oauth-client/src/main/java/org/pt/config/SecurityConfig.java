package org.pt.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SecurityConfig
 * @Author pt
 * @Description
 * @Date 2025/3/1 17:13
 **/
@Configuration
@EnableRedisHttpSession
public class SecurityConfig {
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final OAuth2AccessTokenResponseClient tokenResponseClient;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository, OAuth2AccessTokenResponseClient tokenResponseClient) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.tokenResponseClient = tokenResponseClient;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 允许所有人访问根路径和 OAuth2 重定向端点
                        .requestMatchers("/", "/login/oauth2/code/*").permitAll()
                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                )
                // 配置表单登录（可选）
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                // 调用封装的 OAuth2 登录配置方法
                .oauth2Login(this::configureOAuth2Login);

        return http.build();
    }

    // 封装 OAuth2 登录的自定义配置
    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2){
        oauth2
                // 配置重定向端点，授权服务器（如 GitHub）会将授权码发送到这里
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/login/oauth2/code/*") // 与 ClientRegistration 中的 redirectUri 一致
                )
                // 使用之前定义的 ClientRegistrationRepository（如 GitHub 配置）
                .clientRegistrationRepository(clientRegistrationRepository)
                // 自定义授权端点行为
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorize") // 可选：覆盖默认授权 URI
                        // 将授权请求存储在会话中，使用默认的存储方式
                        .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
                )
                // 自定义令牌端点行为（获取访问令牌）
                .tokenEndpoint(token -> token
                        .accessTokenResponseClient(tokenResponseClient)// 默认令牌交换客户端
                )
//                // 自定义用户信息端点
//                .userInfoEndpoint(userInfo -> userInfo
//                        .userService(customOAuth2UserService) // 使用自定义用户服务
//                )
                // 自定义登录成功和失败的处理逻辑
                .successHandler((request, response, authentication) -> {
                    System.out.println("--------------------授权成功----------------");
                    System.out.println("登录成功，用户: " + authentication.getName());
                    // 设置响应内容类型为 HTML
                    response.setContentType("application/json;charset=UTF-8");
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 构造 JSON 数据
                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("status", "success");
                    responseData.put("message", "登录成功");
                    responseData.put("authentication", authentication);
                    responseData.put("redirect", "/dashboard");
                    // 使用 ObjectMapper 直接写入响应
                    PrintWriter out = response.getWriter();
                    objectMapper.writeValue(out, responseData);
                    out.flush();

                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("--------------------授权失败----------------");
                    System.out.println("登录失败: " + exception.getMessage());
                    response.sendRedirect("/login?error"); // 失败后重定向到登录页并附带错误信息
                });
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}