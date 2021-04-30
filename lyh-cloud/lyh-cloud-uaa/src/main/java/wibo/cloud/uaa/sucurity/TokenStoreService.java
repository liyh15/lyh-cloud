package wibo.cloud.uaa.sucurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

/**
 * @Classname TokenStoreService
 * @Description TODO
 * @Date 2021/4/14 11:23
 * @Created by lyh
 */
@Configuration
public class TokenStoreService extends DefaultTokenServices {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.tokenStore, "tokenStore must be set");
    }

    /**
     * 实现自定义获取令牌，实现登录踢出
     * @param authentication
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/4/14 12:50
     */
    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        OAuth2AccessToken existingAccessToken = this.tokenStore.getAccessToken(authentication);
        if (existingAccessToken != null) {
            // TODO 使用这个进行登录下线
            this.tokenStore.removeAccessToken(existingAccessToken);
        }
        return super.createAccessToken(authentication);
    }
}
