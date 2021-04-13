package wibo.cloud.security.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MinioConfig
 * @Description TODO
 * @Date 2021/3/22 14:23
 * @Created by lyh
 */
@Configuration
public class MinioConfig {

    @Autowired
    private MinioConfigProperties configProperties;

    public static void main(String[] args) {
        int a = 2;
        a ++;

        Integer b = 3;
        b ++;
        MinioConfig minioConfig = new MinioConfig();
        minioConfig.getMy().getMy().getMy();
        System.out.println(b);
        // as的接口受打击四大四大
        System.out.println("aasdasd阿斯达阿斯达四大");

    }

    public MinioConfig getMy()
    {
        return this;
    }


    public MinioClient getMinioClient()
    {
        return MinioClient.builder().endpoint(configProperties.getUrl()).credentials(configProperties.getAccessKey(), configProperties.getSecretKey()).build();
    }
}
