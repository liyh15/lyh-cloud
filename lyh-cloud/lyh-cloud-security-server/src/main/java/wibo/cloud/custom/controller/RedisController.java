package wibo.cloud.custom.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisController
 * @Description TODO
 * @Date 2020/8/10 10:33
 * @Created by lyh
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/put",method = RequestMethod.GET)
    public String put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return "true";
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @RequestMapping(value = "/lock",method = RequestMethod.GET)
    public String lock(String key) throws InterruptedException {
        boolean a = false;
        try {
            RLock rLock = redissonClient.getLock(key);
            // TODO 如果此key已经有锁，则返回false,如果填了时间，则最长等待两秒，两秒中如果没有获得锁则释放锁
            a = rLock.tryLock(2,  TimeUnit.SECONDS);
            System.out.println(a + key);
            Thread.sleep(5000);
            return "lock";
        } finally {
            if (a) {
                redissonClient.getLock(key).unlock();
            }
        }

    }

    @RequestMapping(value = "/lockByTime",method = RequestMethod.GET)
    public String lockByTime(String key, Long time) throws InterruptedException {
        boolean a = false;
        System.out.println("aaaaaaaaaaaaaaa");
        try {
            RLock rLock = redissonClient.getLock(key);
            a = rLock.tryLock(2,5, TimeUnit.SECONDS);
            System.out.println(a + key);
            Thread.sleep(10000);
            return "lock";
        } finally {
            if (a) {
                redissonClient.getLock(key).unlock();
            }
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() throws InterruptedException {
        System.out.println("Aaaaa");
        Thread.sleep(10000);
        return "aaaa";
    }

}
