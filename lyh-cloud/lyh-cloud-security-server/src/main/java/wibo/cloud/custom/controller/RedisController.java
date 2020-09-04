package wibo.cloud.custom.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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
            // TODO 如果此key已经有锁，则返回false,如果填了时间，则最长等待两秒，两秒内如果没有获得锁则返回false
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
            // TODO 如果此key已被加锁，则等待两秒，如果两秒内没有获取锁，则返回false，如果成功加锁而且没有在5秒内释放锁，则自动释放锁
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

    @RequestMapping(value = "/inc",method = RequestMethod.GET)
    public String inc() throws InterruptedException {
        redisTemplate.opsForValue().increment("aaaaaaa",1);
        return "aaaa";
    }

    @RequestMapping(value = "/str",method = RequestMethod.GET)
    public String str(String value) throws InterruptedException {
        redisTemplate.opsForValue().set("bbbbbbb",value);
        return "aaaa";
    }

    @RequestMapping(value = "/obj",method = RequestMethod.GET)
    public String obj() throws InterruptedException {
        redisTemplate.opsForValue().set("cccccc", new Test());
        return "aaaa";
    }

    @RequestMapping(value = "/gettttt",method = RequestMethod.GET)
    public String get() throws InterruptedException {
        Test test = (Test) redisTemplate.opsForValue().get("cccccc");
        System.out.println(redisTemplate.opsForValue().get("cccccc"));
        return "aaaa";
    }

    @RequestMapping(value = "/hash",method = RequestMethod.GET)
    public String hash() throws InterruptedException {
        redisTemplate.opsForHash().put("hash","aaa","bbb");
        redisTemplate.opsForHash().put("hash","bbb","bbb");
        redisTemplate.opsForHash().put("hash","ccc","bbb");
        redisTemplate.opsForHash().put("hash","ddd","bbb");
        System.out.println(redisTemplate.opsForHash().values("hash"));
        return "aaaa";
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
       /*
          getClasses得到该类及其父类所有的public的内部类。
          getDeclaredClasses得到该类所有的public内部类，除去父类的。
        */
        Class c = Integer.class.getDeclaredClasses()[0];
        Field field = c.getDeclaredField("cache");
        field.setAccessible(true);
        Integer[] a = (Integer[]) field.get(c);
        System.out.println(Arrays.toString(a));
    }
}
