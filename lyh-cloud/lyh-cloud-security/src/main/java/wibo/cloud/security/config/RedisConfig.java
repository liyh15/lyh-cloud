package wibo.cloud.security.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
 * config.setBlockWhenExhausted(true);
 *
 * //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
 * config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
 *
 * //是否启用pool的jmx管理功能, 默认true
 * config.setJmxEnabled(true);
 *
 * //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
 * config.setJmxNamePrefix("pool");
 *
 * //是否启用后进先出, 默认true
 * config.setLifo(true);
 *
 * //最大空闲连接数, 默认8个
 * config.setMaxIdle(8);
 *
 * //最大连接数, 默认8个
 * config.setMaxTotal(8);
 *
 * //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
 * config.setMaxWaitMillis(-1);
 *
 * //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
 * config.setMinEvictableIdleTimeMillis(1800000);
 *
 * //最小空闲连接数, 默认0
 * config.setMinIdle(0);
 *
 * //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
 * config.setNumTestsPerEvictionRun(3);
 *
 * //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
 * config.setSoftMinEvictableIdleTimeMillis(1800000);
 *
 * //在获取连接的时候检查有效性, 默认false
 * config.setTestOnBorrow(false);
 *
 * //在空闲时检查有效性, 默认false
 * config.setTestWhileIdle(false);
 *
 * //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
 * config.setTimeBetweenEvictionRunsMillis(-1);
 *
 * JedisPool pool = new JedisPool(config, "localhost",);
 *
 *
 * int timeout=3000;
 * new JedisSentinelPool(master, sentinels, poolConfig,timeout);//timeout 读取超时
 */
@Configuration
public class RedisConfig {

    private static final int IP = 0;

    private static final int PORT = 1;

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    /**
     *
     * 初始化connectionFactory
     */
    public @Bean LettuceConnectionFactory connectionFactory() {
        Map<String, Object> source = new HashMap<String, Object>();
        // 集群配置
        source.put("spring.redis.cluster.nodes", redisClusterProperties.getNodes());
        source.put("spring.redis.cluster.timeout", redisClusterProperties.getTimeout());
        source.put("spring.redis.cluster.max-redirects", redisClusterProperties.getMaxRedirects());
        RedisClusterConfiguration redisClusterConfiguration;
        redisClusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        return new LettuceConnectionFactory(redisClusterConfiguration);
    }

    /**
     * RedisTemplate配置
     *
     * https://blog.csdn.net/xiaodujava/article/details/82190618 三种对象json之间的转换说明
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {

        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        /**
         * * 将string串转化为对象
         *  * mapper.readValue(jsonString, Person.class);
         *  *
         *  * 将对象转化为json格式的字符串。
         *  * mapper.writeValueAsString(person);
         *  *
         *  *
         *  * Include.ALWAYS  是序列化对像所有属性(默认)
         *  *
         *  * Include.NON_NULL 只有不为null的字段才被序列化,属性为NULL 不序列化
         *  *
         *  * Include.NON_EMPTY 如果为null或者 空字符串和空集合都不会被序列化
         *  *
         *  * Include.NON_DEFAULT 属性为默认值不序列化
         */
        ObjectMapper om = new ObjectMapper();
        /**
         *  如果需要保留大写，给类添加@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
         *  这个注解将getter生成的字段给隐藏掉，getter就是生成小写的方法
         *  PropertyAccessor.ALL的规则是通过属性名和getter方法同时获得属性，直接通过属性名获取不区分大小写，但是getter方法获取会属性的第一个字母给小写化，
         *  所以如果属性的第一个字母大写的话如果用PropertyAccessor.ALL就会出现生成的json串里面会有两个相同的属性首字母大小写不相同，所以命名属性名的时候还是
         *  用驼峰命名法就不会出现这个问题
         *   @JsonDeserialize(as = Apple.class) 注释在接口属性上会指定反序列化的实体类
         *
         */
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY); // JsonAutoDetect.Visibility.ANY表示所有类型的属性
        // 空属性不会转化成json
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// TODO Hash key序列化
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());// TODO Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


     static interface Fruit {
     }


    static class Apple implements Fruit {
        private String apple = "apple";

         public String getApple() {
             return apple;
         }

         public void setApple(String apple) {
             this.apple = apple;
         }
     }


    static class Test {
        private String onePerson = "111";

        private String aAa = "BBB";

        private String vvv = "plk";

        @JsonDeserialize(as = Apple.class)
        private Fruit fruit;

        public Fruit getFruit() {
            return fruit;
        }

        public void setFruit(Fruit fruit) {
            this.fruit = fruit;
        }

        public String getOnePerson() {
            return onePerson;
        }

        public void setOnePerson(String onePerson) {
            this.onePerson = onePerson;
        }

        public String getaAa() {
            return aAa;
        }

        public void setaAa(String aAa) {
            this.aAa = aAa;
        }

        public String getVvv() {
            return vvv;
        }

        public void setVvv(String vvv) {
            this.vvv = vvv;
        }
    }

    public static void main(String[] args) throws IOException {
         Test test = new Test();
         Apple apple = new Apple();
         test.setFruit(apple);
         ObjectMapper om = new ObjectMapper();
         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
         System.out.println(om.writeValueAsString(test));
         Test test1 = om.readValue(om.writeValueAsString(test), Test.class);
         System.out.println(test1);
    }

}
