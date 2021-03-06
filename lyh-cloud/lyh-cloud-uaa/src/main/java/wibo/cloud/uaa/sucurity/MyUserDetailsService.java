package wibo.cloud.uaa.sucurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import wibo.cloud.uaa.dto.UserDto;
import wibo.cloud.uaa.mapper.UserMapper;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname MyUserDetailsService
 * @Description 我自定义的detailService
 * @Date 2020/7/16 11:32
 * @Created by lyh
 */
@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userMapper.getUserByName(username);
        Set<String> permission = new HashSet<>();
        userDto.getUserAuth().forEach(e -> {
            permission.add(e.getAuth());
        });

        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(permission.toArray(new String[0]));

        // TODO 如果需要自定义用户类，直接继承User对象即可
        UserDetails userDetails = User.withUsername(username).password(userDto.getPassword()).authorities(authorities).build();
        return userDetails;
    }
}
