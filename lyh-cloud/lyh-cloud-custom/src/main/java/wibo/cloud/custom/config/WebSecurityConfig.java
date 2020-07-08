/*
package wibo.cloud.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    // 定义用户信息服务(查询用户信息)
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager  = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("lyh").password(bCryptPasswordEncoder.encode("123")).authorities("p1").build());
        manager.createUser(User.withUsername("hzy").password(bCryptPasswordEncoder.encode("123")).authorities("p2").build());
        return manager;
    }

    // 密码编辑器
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 这个表示不需要对密码进行编码
        return new BCryptPasswordEncoder();
    }

    // 配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()   // 表示对请求进行鉴权
                .antMatchers("/r/**").authenticated() // 所有/r/**的请求必须认证通过
                .anyRequest().permitAll()
                .and()
                .formLogin() // 允许表单登录，也就是security自定义的登录方式
                .loginProcessingUrl("/loginn"); // 设置验证账号密码的路径
                // .successForwardUrl("/r/name"); // 自定义登录成功跳转的地址，如果是网站的话需要跳到主页
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // 表示有需要就创建一个session
     }

}
*/
