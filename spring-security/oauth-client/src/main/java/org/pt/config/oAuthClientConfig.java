package org.pt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @ClassName oAuthClientConfig
 * @Author pt
 * @Description
 * @Date 2025/3/1 18:15
 **/
@Configuration
public class oAuthClientConfig {


//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
//        return (userRequest) -> {
//            // 获取令牌信息
//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            System.out.println("令牌信息: ");
//            System.out.println("  Access Token: " + accessToken.getTokenValue());
//            System.out.println("  Token Type: " + accessToken.getTokenType().getValue());
//            System.out.println("  Scopes: " + accessToken.getScopes());
//            System.out.println("  Expires At: " + accessToken.getExpiresAt());
//            // 获取用户信息 这个是根据token令牌获取用户信息
//            //OAuth2User oAuth2User = delegate.loadUser(userRequest);
//            RestTemplate restTemplate = new RestTemplate();
//            Authentication authentication = restTemplate.getForObject("http://localhost:9000/user",Authentication.class);
//            if (authentication.getPrincipal() == null) throw new OAuth2AuthenticationException("用户名为空");
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("username",authentication.getPrincipal());
//            // 构造 OAuth2User
//            return new DefaultOAuth2User(authentication.getAuthorities(), map, "login");
//        };
//    }


    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> customTokenResponseClient() {
        RestTemplate restTemplate = new RestTemplate();
        return new OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>() {
            @Override
            public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
                // 获取 OAuth2AuthorizationExchange 对象
                OAuth2AuthorizationExchange authorizationExchange = authorizationGrantRequest.getAuthorizationExchange();
                // 输出调试信息
                System.out.println("=== OAuth2AuthorizationExchange 属性信息 ===");
                OAuth2AuthorizationRequest authorizationRequest = authorizationExchange.getAuthorizationRequest();
                System.out.println("授权请求 (Authorization Request):");
                System.out.println("  - Grant Type: " + authorizationRequest.getGrantType().getValue());
                System.out.println("  - Client ID: " + authorizationRequest.getClientId());
                System.out.println("  - Redirect URI: " + authorizationRequest.getRedirectUri());
                System.out.println("  - Scopes: " + authorizationRequest.getScopes());
                System.out.println("  - State: " + authorizationRequest.getState());
                System.out.println("  - Authorization URI: " + authorizationRequest.getAuthorizationUri());
                System.out.println("  - Additional Parameters: " + authorizationRequest.getAdditionalParameters());

                OAuth2AuthorizationResponse authorizationResponse = authorizationExchange.getAuthorizationResponse();
                System.out.println("授权响应 (Authorization Response):");
                System.out.println("  - Code: " + authorizationResponse.getCode());
                System.out.println("  - State: " + authorizationResponse.getState());
                System.out.println("  - Redirect URI: " + authorizationResponse.getRedirectUri());
                System.out.println("  - Error (if any): " + authorizationResponse.getError());
                System.out.println("============================");
                // 获取授权码和客户端注册信息
                String code = authorizationResponse.getCode();
                ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
                // 构造请求头
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth(clientRegistration.getClientId(), clientRegistration.getClientSecret());
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                // 构造请求体
                String body = String.format(
                        "grant_type=authorization_code&code=%s&redirect_uri=%s",
                        code, clientRegistration.getRedirectUri()
                );
                // 发送请求到自定义授权服务器的令牌端点
                RequestEntity<String> requestEntity = RequestEntity
                        .post(clientRegistration.getProviderDetails().getTokenUri()) // http://localhost:9000/oauth2/token
                        .headers(headers)
                        .body(body);
                System.out.println("requestEntity-------->"+requestEntity);
                ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
                // 修改日志，避免调用 getUrl()
                System.out.println("发送令牌请求: " + clientRegistration.getProviderDetails().getTokenUri() + ", Body: " + body);
                System.out.println("令牌响应状态码: " + response.getStatusCodeValue());
                System.out.println("令牌响应头: " + response.getHeaders());
                System.out.println("令牌响应体: " + response.getBody());
                // 解析响应
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map<String, Object> responseBody = response.getBody();
                    System.out.println("令牌响应: " + responseBody);
                    String accessToken = (String) responseBody.get("access_token");
                    String tokenType = (String) responseBody.get("token_type");
                    String scope = (String) responseBody.get("scope");
                    String idToken = (String) responseBody.get("id_token"); // 提取 id_token
                    System.out.println("--------------------");
                    System.out.println("accessToken------->"+accessToken);
                    System.out.println("tokenType------->"+tokenType);
                    System.out.println("scope------->"+scope);
                    System.out.println("--------------------");
                    Integer expiresIn = responseBody.get("expires_in") != null ? ((Number) responseBody.get("expires_in")).intValue() : null;
                    if (accessToken == null || accessToken.trim().isEmpty()) {
                        throw new IllegalStateException("授权服务器令牌响应缺少 access_token: " + responseBody);
                    }
                    if (tokenType == null || !"bearer".equalsIgnoreCase(tokenType.trim())) {
                        throw new IllegalStateException("授权服务器令牌响应不支持 token_type: " + tokenType + "，预期为 bearer");
                    }
                    Set<String> scopes = (scope != null && !scope.trim().isEmpty())
                            ? new HashSet<>(Arrays.asList(scope.trim().split("\\s+")))
                            : Collections.emptySet();

                    OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken)
                            .tokenType(OAuth2AccessToken.TokenType.BEARER)
                            .scopes(scopes);

                    if (expiresIn != null) {
                        builder.expiresIn(expiresIn.longValue()); // 添加过期时间
                    }
                    if (idToken != null) {
                        // 将 id_token 添加到 additionalParameters
                        builder.additionalParameters(Collections.singletonMap("id_token", idToken));
                    }
                    return builder.build();
                } else {
                    throw new IllegalStateException("获取令牌失败，状态码: " + response.getStatusCodeValue());
                }
            }
        };
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        // 指定客户端注册的唯一标识符，在Spring中用于区分不同的客户端配置，例如对应application.yml中的spring.security.oauth2.client.registration.b
        ClientRegistration b = ClientRegistration.withRegistrationId("b")
                // 客户端ID，由授权服务器分配，用于标识此客户端，需与Server端注册的client-id一致，在授权请求中作为client_id参数
                .clientId("b-client")
                // 客户端密钥，明文形式，用于验证客户端身份，需与Server端注册的密钥匹配（去掉可能的{noop}前缀），通过Basic认证发送
                .clientSecret("b-secret")
                // 指定OAuth2授权类型，这里是授权码模式，Client先获取code再换取token，最安全常见的模式
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // 重定向URI，Server授权后将用户重定向到此地址并附上授权码，必须与Server端注册的redirect-uris一致
                .redirectUri("http://localhost:5555/login/oauth2/code/b")
                // 请求的权限范围，定义Client需要的权限，Server需支持这些scope，用户授权时会看到这些范围
                .scope("read", "write","profile", OidcScopes.OPENID)
                // 授权服务器的授权端点URL，Client将用户重定向到此地址开始OAuth2流程，用户在此登录并授权
                .authorizationUri("http://localhost:9000/oauth2/authorize")
                // 授权服务器的令牌端点URL，Client用授权码向此地址请求访问令牌，通常是POST请求
                .tokenUri("http://localhost:9000/oauth2/token")
                .jwkSetUri("http://localhost:9000/oauth2/jwks")
                .userInfoUri("http://localhost:9000/userinfo") // 添加 UserInfo URI
                .userNameAttributeName("sub")
                // 完成ClientRegistration对象的构建，将所有参数组合成完整的客户端注册配置
                .build();
        // 创建内存中的客户端注册仓库，存储刚定义的ClientRegistration，适合开发测试，生产可用数据库实现
        return new InMemoryClientRegistrationRepository(b);
    }


}

