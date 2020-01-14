package com.hx.oauth.simple.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author houxi
 * @version 1.0
 * @date 2020/1/13 14:57
 */
@Configuration
@EnableAuthorizationServer
@Order(6)
public class SsoAuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 客户端一些配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret((passwordEncoder.encode("secret1")))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all", "read", "write")
                .autoApprove(true)
                .redirectUris("http://www.baidu.com")
//                .redirectUris("http://localhost:8081/login")
                .and()
                .withClient("client2")
                .secret((passwordEncoder.encode("secret2")))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all", "read", "write")
                .autoApprove(true)
                .redirectUris("http://localhost:8082/login");
    }
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
}

