package wibo.cloud.uaa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MyBatisProperties
 * @Description TODO
 * @Date 2021/4/13 9:21
 * @Created by lyh
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class MyBatisProperties {

    private String driverClassName;

    private String jdbcUrl;

    private String userName;

    private String password;
}
