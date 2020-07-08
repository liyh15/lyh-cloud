package wibo.cloud.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 生成令牌的配置类
 */
@Configuration
public class TokenConfig {
    @Bean
    public TokenStore tokenStore() {
        // 令牌持久化的方式，暂时使用内存式来进行令牌的持久化
        return new InMemoryTokenStore();
    }
}
