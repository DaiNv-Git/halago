package com.hitex.halago.security;

import com.google.auth.oauth2.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Bean
    public org.springframework.security.oauth2.provider.token.TokenStore tokenStore() {
        return (org.springframework.security.oauth2.provider.token.TokenStore) new JdbcTokenStore(dataSource);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("curl")
                .authorities("ROLE_ADMIN")
                .resourceIds("jaxenter")
                .scopes("read", "write")
                .authorizedGrantTypes("client_credentials")
                .secret("password")
                .and()
                .withClient("web")
                .redirectUris("http://halago.semob.info/thanh-vien")
                .resourceIds("jaxenter")
                .scopes("read")
                .authorizedGrantTypes("implicit");
    }
}
