package wibo.cloud.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class MySqlConfiguration {

    private String driverClassName;

    private String jdbcUrl;

    private String userName;

    private String password;
}
