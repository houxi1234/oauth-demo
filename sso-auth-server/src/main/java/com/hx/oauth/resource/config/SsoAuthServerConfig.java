package com.hx.oauth.resource.config;


import org.springframework.context.annotation.Configuration;
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
public class SsoAuthServerConfig extends AuthorizationServerConfigurerAdapter {

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
                .secret("secret1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all", "read", "write")
                .autoApprove(true)
                .and()
                .withClient("client2")
                .secret("secret2")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all", "read", "write")
                .autoApprove(false);
    }
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll")
                .checkTokenAccess("isAuthenticated()");
    }
}

