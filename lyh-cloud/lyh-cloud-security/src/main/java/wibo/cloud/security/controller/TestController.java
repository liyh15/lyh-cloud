package wibo.cloud.security.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import wibo.cloud.security.config.BeanUtil;
import wibo.cloud.security.config.ScopeBean;
import wibo.cloud.security.service.TestService;
import wibo.cloud.security.service.impl.TwoServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/2/21 11:24
 * @Created by lyh
 */
@RestController
public class TestController {

    @Autowired
    private ScopeBean scopeBean;

    @Autowired
    private TestService testService;

    private String aaa = "Asdasd";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping("mysql1")
    public String mysql1() {
        System.out.println("asdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return "test";
    }

    @PostMapping("test")
    public String test() {
        System.out.println("asdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
      return "test";
    }

    @PostMapping("test2")
    public String test2(@RequestParam("lyh") String lyh, @RequestParam("name") String name) {
        String a = testService.aaa();
        System.out.println(a);
        return "test2";
    }

    @PostMapping("exception")
    public String exception() throws Exception {
        System.out.println("fghfghfgh");
        throw new Exception("Asd");
    }

    @GetMapping("scope")
    public String scope() throws Exception {
        scopeBean.aaa();
        return "asdads";
    }

    @PostMapping("redis")
    public String redis() throws Exception {
        redisTemplate.opsForValue().increment("aaaaa", 1);
        Integer a = 1;
        int b = a;
        return "asdads";
    }

    @PostMapping("redisMessage")
    public String redisMap() throws Exception {
        redisTemplate.convertAndSend("chat","hello world");
        return "asdads";
    }

    @PostMapping("redisMap")
    public String redisMessage() {
        redisTemplate.opsForHash().put("lyh","key","value");
        redisTemplate.opsForHash().put("lyh","key2","value2");
        System.out.println(redisTemplate.opsForHash().values("lyh"));
        System.out.println(redisTemplate.opsForHash().entries("lyh"));
        System.out.println(redisTemplate.opsForHash().keys("lyh"));
        return "asdads";
    }

    @PostMapping("redisList")
    public String redisList() {
        redisTemplate.opsForList().leftPush("list","aaa");
        List<String> list = new ArrayList<>();
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        redisTemplate.opsForList().leftPushAll("list", list);

        System.out.println(redisTemplate.opsForList().index("list",0));

        return "asdads";
    }

    @PostMapping("redisZset")
    public String redisZset() {

        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        Set<ZSetOperations.TypedTuple<Object>> set2 = new HashSet<>();
        DefaultTypedTuple typedTuple1 = new DefaultTypedTuple("aaa", 11D);
        DefaultTypedTuple typedTuple2 = new DefaultTypedTuple("bbb", 55D);
        DefaultTypedTuple typedTuple3 = new DefaultTypedTuple("ccc", 16D);
        DefaultTypedTuple typedTuple4 = new DefaultTypedTuple("ddd", 89D);
        DefaultTypedTuple typedTuple5 = new DefaultTypedTuple("eee", 45D);
        DefaultTypedTuple typedTuple6 = new DefaultTypedTuple("fff", 34D);

        DefaultTypedTuple typedTuple7 = new DefaultTypedTuple("ggg", 12D);

        DefaultTypedTuple typedTuple8 = new DefaultTypedTuple("hhh", 34D);
        DefaultTypedTuple typedTuple9 = new DefaultTypedTuple("iii", 12D);


        set.add(typedTuple1);
        set.add(typedTuple2);
        set.add(typedTuple3);
        set.add(typedTuple4);
        set.add(typedTuple5);
        set.add(typedTuple6);
        set.add(typedTuple7);

        set2.add(typedTuple7);
        set2.add(typedTuple8);
        set2.add(typedTuple9);



        redisTemplate.opsForZSet().add("zSet", set);
        redisTemplate.opsForZSet().add("zSet2", set2);


        // TODO 统计总数
        System.out.println(redisTemplate.opsForZSet().zCard("zSet"));

        // TODO 取出分数在11-45之间的个数
        System.out.println(redisTemplate.opsForZSet().count("zSet", 11, 45));

        // TODO 从小标1开始截取5个值，不返回分数
        System.out.println(redisTemplate.opsForZSet().range("zSet", 1, 5));

        // TODO 获取所有元素，并返回分数，进行排名
        System.out.println(redisTemplate.opsForZSet().rangeWithScores("zSet", 0, -1));

        // TODO 吧zSet1和zSet2的交集放入zSet3
        redisTemplate.opsForZSet().intersectAndStore("zSet", "zSet2", "zSet3");

        RedisZSetCommands.Range range = RedisZSetCommands.Range.range();
        range.lt("ddd"); // TODO 小于
        range.gt("aaa"); // TODO 大于

        // TODO 区间
        System.out.println(redisTemplate.opsForZSet().rangeByLex("zSet", range));

        range.lte("ddd"); // TODO 小于等于
        range.gte("aaa"); // TODO 大于等于

        System.out.println(redisTemplate.opsForZSet().rangeByLex("zSet", range));

        RedisZSetCommands.Limit limit = RedisZSetCommands.Limit.limit();
        // TODO 限制返回个数
        limit.count(4);
        // TODO 从第2个开始取
        limit.offset(2);
        System.out.println(redisTemplate.opsForZSet().rangeByLex("zSet", range, limit));

        // TODO 求排名
        System.out.println(redisTemplate.opsForZSet().rank("zSet", "ddd"));

        // TODO 删除元素并返回个数
        System.out.println(redisTemplate.opsForZSet().remove("zSet", "ddd"));

        // TODO 删除排名第2和第3的元素
        System.out.println(redisTemplate.opsForZSet().removeRange("zSet", 1,2));

        // TODO 给集合中的一个元素加11
        redisTemplate.opsForZSet().incrementScore("zSet", "aaa",11);

        return "asdads";
    }

    @PostMapping("redisTransation")
    public String redisTransation() {
        SessionCallback callback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {

                redisOperations.multi();
                redisOperations.boundValueOps("lyh").set("aaaaaaaaaaaaaaaaa");

                String value = (String) redisOperations.boundValueOps("lyh").get();

                System.out.println(value);

                List list = redisOperations.exec();

                System.out.println(list);

                value = (String) redisTemplate.opsForValue().get("lyh");

                return value;
            }
        };

        String value = (String) redisTemplate.execute(callback);

        return value;
    }

    @PostMapping("exec")
    public DeferredResult<String> exec() throws InterruptedException {
        DeferredResult<String> deferredResult = new DeferredResult<>(10000L,"ertertertert");
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("asdasd");
            }
        });
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("bbbbbbbbbb");
            }
        });
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(5000);
                deferredResult.setResult("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        }).start();

        return deferredResult;
    }

    @PostMapping("getBean")
    public String  getBean() {
        TwoServiceImpl testService = (TwoServiceImpl) BeanUtil.getBeanByName("twoServiceImpl");
        TwoServiceImpl testService2 = (TwoServiceImpl) BeanUtil.getBean(TwoServiceImpl.class);

        testService.aaa();
        return "hello";
    }

    @PostMapping("uploadFile")
    public String  uploadFile(@RequestParam("file")MultipartFile multipartFile) {
        System.out.println(multipartFile.getOriginalFilename());
        return "uploadFile";
    }
}
