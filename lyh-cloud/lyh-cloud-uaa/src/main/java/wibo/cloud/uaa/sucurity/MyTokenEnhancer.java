package wibo.cloud.uaa.sucurity;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MyTokenEnhancer
 * @Description 自定义token增强器（返回带上自定义参数）
 * @Date 2021/4/15 11:09
 * @Created by lyh
 */
@Configuration
public class MyTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        User user = (User) oAuth2Authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("age","123");
        map.put("kkk","asdasdasd");
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(map);
        return oAuth2AccessToken;
    }
}
