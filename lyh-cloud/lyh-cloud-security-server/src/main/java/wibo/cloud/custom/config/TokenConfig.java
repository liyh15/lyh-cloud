package wibo.cloud.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import wibo.cloud.custom.controller.TestController;

/**
 * 生成令牌的配置类
 */
@Configuration
public class TokenConfig extends  TestController{

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 通过这个缓存来查询缓存令牌
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2020/7/20 18:03
     */
    @Bean
    public TokenStore tokenStore() {
        // 令牌持久化的方式，暂时使用内存式来进行令牌的持久化
        return new RedisTokenStore(redisConnectionFactory);
    }
}
