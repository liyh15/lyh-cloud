package wibo.cloud.custom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 资源服务器的配置类
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1") // 配置资源id，这里的资源id和授权服务器中的资源id一致
                .stateless(true); // 设置这些资源仅基于令牌认证,也就是access_token
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/r/**").authenticated()
                .anyRequest().permitAll() // 表示释放所有的请求，必须写在释放的路径后面
                //.anyRequest().authenticated() // 表示拦截所有的请求，必须写在释放的路径后面
                .and().csrf().disable();
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = 3;
        double d = 1D;
        String cc = "333";
        Date date = new Date();
    }
}
