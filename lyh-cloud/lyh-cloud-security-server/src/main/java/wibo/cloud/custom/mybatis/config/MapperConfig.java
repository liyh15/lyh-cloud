package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MapperConfig
 * @Description TODO
 * @Date 2021/1/25 14:28
 * @Created by lyh
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "lyh.mybatis")
public class MapperConfig {

    private String mapperPath;
}

