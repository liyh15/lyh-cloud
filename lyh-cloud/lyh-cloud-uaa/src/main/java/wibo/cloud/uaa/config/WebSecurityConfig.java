package wibo.cloud.uaa.config;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Bean
    public UserDetailsService userDetailsService() {
        /*InMemoryUserDetailsManager manager  = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("lyh").password(bCryptPasswordEncoder.encode("123")).authorities("p1").build());
        manager.createUser(User.withUsername("hzy").password(bCryptPasswordEncoder.encode("123")).authorities("p2").build());*/
        return new MyUserDetailsService();
    }

    // 密码编辑器
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 这个表示seucrity使用bcryty解密
        return new BCryptPasswordEncoder();
    }

    // 配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()   // 表示对请求进行鉴权
                .antMatchers("/login*").permitAll().anyRequest().authenticated()
                .and()
                .formLogin() // 允许表单登录，也就是security自定义的登录方式
                .loginProcessingUrl("/loginn"); // 设置验证账号密码的路径,security表单登录时的请求路径会按照这个命名
                // .successForwardUrl("/r/name"); // 自定义登录成功跳转的地址，如果是网站的话需要跳到主页
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // 表示有需要就创建一个session
        //always 如果没有session存在就创建一个 ifRequired 如果需要就创建一个Session（默认）登录时
        // never SpringSecurity 将不会创建Session，但是如果应用中其他地方创建了Session，那么Spring Security将会使用它。
        // stateless SpringSecurity将绝对不会创建Session，也不使用Session
        // 默认情况下，Spring Security会为每个登录成功的用户会新建一个Session，就是ifRequired 。
        // 若选用never，则指示Spring Security对登录成功的用户不创建Session了，但若你的应用程序在某地方新建了 session，那么Spring Security会用它的。
        // 若使用stateless，则说明Spring Security对登录成功的用户不会创建Session了，你的应用程序也不会允许新建 session。并且它会暗示不使用cookie，所以每个请求都需要重新进行身份验证。这种无状态架构适用于REST API 及其无状态认证机制。
     }

}
