package wibo.cloud.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MinioConfigProperties
 * @Description TODO
 * @Date 2021/3/22 14:24
 * @Created by lyh
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfigProperties {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
