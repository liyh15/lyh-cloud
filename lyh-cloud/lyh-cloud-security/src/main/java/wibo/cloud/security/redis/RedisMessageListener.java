package wibo.cloud.security.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Classname RedisMessageListener
 * @Description TODO
 * @Date 2021/3/16 14:35
 * @Created by lyh
 */
@Configuration
public class RedisMessageListener implements MessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String value = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        System.out.println(value);
        byte [] channel = message.getChannel();

        String channelName = (String) redisTemplate.getStringSerializer().deserialize(channel);
        System.out.println(channelName);
    }
}
