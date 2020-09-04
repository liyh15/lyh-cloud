package wibo.cloud.custom.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RedissonConfig
 * @Description TODO
 * @Date 2020/8/10 15:38
 * @Created by lyh
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://10.11.1.188:7001","redis://10.11.1.188:7002","redis://10.11.1.188:7003","redis://10.11.1.188:7004","redis://10.11.1.188:7005","redis://10.11.1.188:7006");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
