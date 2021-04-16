package wibo.cloud.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableGlobalMethodSecurity(prePostEnabled = true) // 如果使用@PreAuthority注解，必须要
@ComponentScan(basePackages = "wibo.cloud")
public class Custom2Application {
    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        Collections.sort(list);
        System.out.println(list);
        //SpringApplication.run(Custom2Application.class, args);
    }
}
