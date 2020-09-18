package wibo.cloud.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @Classname FastDfsApplication
 * @Description TODO
 * @Date 2020/9/10 9:18
 * @Created by lyh
 */
@Import(FdfsClientConfig.class)
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "wibo.cloud")
public class FastDfsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastDfsApplication.class, args);
    }
}
