package wibo.cloud.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import wibo.cloud.security.netty.NettyServer;

import java.net.InetSocketAddress;


/**
 * @Classname SecurityApplication
 * @Description TODO
 * @Date 2021/2/21 11:09
 * @Created by lyh
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true) // 如果使用@PreAuthority注解，必须要
@ComponentScan(basePackages = "wibo.cloud")
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
        //启动服务端
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress("127.0.0.1", 8091));
    }
}
