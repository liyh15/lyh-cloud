package wibo.cloud.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Classname WebSecurityConfig
 * @Description TODO
 * @Date 2021/2/21 11:34
 * @Created by lyh
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     /**
      * TODO Security是通过各个Filter来执行的
      */

     @Override
     // @Bean
     public UserDetailsService userDetailsService() {
         // 创建内存型用户管理类
         InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
         manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
         manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
         return manager;
     }

    @Bean
    public PasswordEncoder passwordEncoder() {
         // 临时创建一个不需要加密
         return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         // 配置全线路径，先局部配置，然后再整体配置
        http.authorizeRequests().antMatchers("/se/**").authenticated().anyRequest().permitAll().and().formLogin().and().csrf().disable();
    }
}
