package wibo.cloud.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableEurekaClient
@EnableGlobalMethodSecurity(prePostEnabled = true) // 如果使用@PreAuthority注解，必须要
@ComponentScan(basePackages = "wibo.cloud")
public class Custom2Application {
    public static void main(String[] args) {
        SpringApplication.run(Custom2Application.class, args);
    }
}
