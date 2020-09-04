package wibo.cloud.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置OAuth2.0授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    // 令牌的生成和保存方式
    @Autowired
    private TokenStore redisTokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置客户端详情信息
     * 可用于配置客户端的认证信息，从而实现客户端的筛选功能
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.withClientDetails(clientDetailsService);
      clients.inMemory() // 使用内存存储
              .withClient("client1") // client_id
              .secret(new BCryptPasswordEncoder().encode("secret"))
              .resourceIds("res1") // 表示允许访问的资源标识
              .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token") // 该client允许的授权类型
              .scopes("all") // 允许的授权范围
              .accessTokenValiditySeconds(1800); // 设置accesstoken的过期时间，1800秒
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") // 表示访问jwt公有密钥的端点，表示全部开放
                .checkTokenAccess("permitAll()") // 表示检查token的端点，表示全部开放
                .allowFormAuthenticationForClients(); // 表示支持表单类型
    }

    /**
     * 令牌访问端点的配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)  // 认证管理器，用于密码认证
                .tokenStore(redisTokenStore) // 配置令牌的存储方式, 这里使用reids存储, 表示服务端用redis存储
                .userDetailsService(userDetailsService) // 表示使用密码认证方式的用户配置来源
                .tokenEnhancer(tokenEnhancer()) // 申请令牌返回的数据添加json属性
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); // 支持的访问方法
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @description 申请令牌返回的数据添加json属性
     * @author liyuanhao
     * @date 2020/8/19 15:04
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            additionalInfo.put("tokenEnhancer", "这个是令牌增强器添加的新的json属性");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
