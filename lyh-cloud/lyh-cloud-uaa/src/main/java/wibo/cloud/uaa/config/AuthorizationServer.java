package wibo.cloud.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 配置OAuth2.0授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    // 令牌的生成和保存方式
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**
     * 配置客户端详情信息
     * 可用于配置客户端的认证信息，从而实现客户端的筛选功能，就是类似于ClientDetailService
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
              .accessTokenValiditySeconds(1800). // 设置accesstoken的过期时间，1800秒
              redirectUris("http://www.baidu.com"); // 授权码模式中通过后重定向的地址
//              .autoApprove(true) // 不需要手动点击授权
//              // 加上验证回调地址
//              .redirectUris("http://www.baidu.com");
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
                .tokenStore(tokenStore) // 配置令牌的存储方式
                .userDetailsService(userDetailsService). // 设置这个是用来进行刷新用户判断的
                authorizationCodeServices(authorizationCodeServices) // 配置授权码存储方式
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); // 支持的访问方法
    }

    /**
     * TODO 定义管理令牌的接口类，可以对令牌进行详细的配置
     * @return
     */
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);
        service.setSupportRefreshToken(true);
        service.setTokenStore(tokenStore);
        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    /**
     * 配置授权码的存储方式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        //设置授权码模式的授权码如何 存取，暂时采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }
}
