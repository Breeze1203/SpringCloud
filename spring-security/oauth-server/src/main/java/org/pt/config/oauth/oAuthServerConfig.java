package org.pt.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

/**
 * @ClassName oAuthServerConfig
 * @Author pt
 * @Description
 * @Date 2025/3/2 18:51
 **/
@Configuration(proxyBeanMethods = false)
public class oAuthServerConfig {

    @Bean
    public OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer() {
        OAuth2AuthorizationServerConfigurer configurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        configurer.oidc(Customizer.withDefaults());
        return configurer;
    }


}
