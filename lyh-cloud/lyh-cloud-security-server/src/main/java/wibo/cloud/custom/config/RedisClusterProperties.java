package wibo.cloud.custom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Data
@ConfigurationProperties(prefix = "redis.cluster")
public class RedisClusterProperties {

	/**
	 * 最大idle
	 */
	private static final int DEFAULT_MAXIDLE = 10;

	/**
	 * 最大total
	 */
	private static final int DEFAULT_MAXTOTAL = 500;

	/**
	 * 最大等待时间
	 */
	private static final int DEFAULT_MAXWAITMILLIS = 3000;

	/**
	 * 最大跳转次数
	 */
	private static final int DEFAULT_MAXREDIRECTS = 10;

    //链接超时时间
    private String timeout;

    //集群节点信息:形如 xxx.xxx.xxx.xxx:port,xxx.xxx.xxx.xxx:port
    private String nodes;

    //重连次数
    private String maxAttempts;
    // 密码
    private String password;
    // 最大空闲连接数
    private int maxIdle = DEFAULT_MAXIDLE;
    // 最大连接数
    private int maxTotal = DEFAULT_MAXTOTAL;
    // 最大阻塞时间
    private int maxWaitMillis = DEFAULT_MAXWAITMILLIS;
	// 最大跳转次数
    private int maxRedirects = DEFAULT_MAXREDIRECTS;

    private boolean testOnReturn = false;

    private boolean testOnBorrow = false;
}
