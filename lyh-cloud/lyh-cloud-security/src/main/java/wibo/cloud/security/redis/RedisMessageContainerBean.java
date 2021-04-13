package wibo.cloud.security.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;

/**
 * @Classname RedisMessageContainer
 * @Description TODO
 * @Date 2021/3/16 14:50
 * @Created by lyh
 */
@Configuration
public class RedisMessageContainerBean {

    @Autowired
    private LettuceConnectionFactory connectionFactory;

    @Autowired
    private RedisMessageListener redisMessageListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(3);
        taskExecutor.initialize();


        container.setTaskExecutor(taskExecutor);

        Map map = new HashMap();
        ChannelTopic channelTopic = new ChannelTopic("chat");

        List<ChannelTopic> channelTopics = new ArrayList<>();
        channelTopics.add(channelTopic);
        map.put(redisMessageListener, channelTopics);

        container.setMessageListeners(map);
        return container;
    }
}
