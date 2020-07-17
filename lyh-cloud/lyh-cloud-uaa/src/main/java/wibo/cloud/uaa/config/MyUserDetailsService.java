package wibo.cloud.uaa.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Classname MyUserDetailsService
 * @Description 我自定义的detailService
 * @Date 2020/7/16 11:32
 * @Created by lyh
 */
public class MyUserDetailsService implements UserDetailsService {

    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = User.withUsername(username).password(bCryptPasswordEncoder.encode("123")).authorities("p1").build();
        return userDetails;
    }
}
