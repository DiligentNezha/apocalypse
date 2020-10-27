package com.apocalypse.idaas.config.security.oauth2;

import com.apocalypse.idaas.config.security.oauth2.client.CustomClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description 授权相关配置
 * @date 2020/10/19
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomClientDetailsService customClientDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        super.configure(oauthServer);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("AuthenticationServerConfig 配置");
//        clients.inMemory()
//                .withClient("idaas")
//                .secret(passwordEncoder.encode("secret"))
//                .authorizedGrantTypes("authorization_code", "client_credentials", "password", "refresh-token")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(72000)
//                .autoApprove(true)
//                .redirectUris("http://localhost:33001/call/login/oauth2/code/idaas")
//                .additionalInformation()
//                .resourceIds(ResourceIds.IDAAS)
//                .authorizedGrantTypes("ROLE_CLIENT")
//                .scopes("me", "email", "phone")
//                .autoApprove(false);
        clients.withClientDetails(customClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(tokenStore());
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
