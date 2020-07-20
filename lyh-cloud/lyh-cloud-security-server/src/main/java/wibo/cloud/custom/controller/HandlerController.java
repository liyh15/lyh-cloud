package wibo.cloud.custom.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.custom.config.Login;
import wibo.cloud.custom.config.LoginAnnotation;
import wibo.cloud.custom.config.LoginUser;

import java.util.Date;


/**
 * 此Controller主要测试HandlerInterceptorAdapter接口和HandlerMethodArgumentResolver类
 * 一个是针对接口方法的拦截，一个是针对接口方法入参的拦截
 */
@RestController
@RequestMapping("/handler")
public class HandlerController {

    @Login
    @RequestMapping(value = "/login",  method = RequestMethod.GET)
    public String login(@LoginAnnotation LoginUser user) {
       return user.getUserId();
    }

    /**
     * 加了这个注解需要走登录校验
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserToken", method = RequestMethod.GET)
    public String getUserToken(Long userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 604800 * 1000); // 设置过期时间一天
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(new Date()) // jwt的签发时间
                .setExpiration(expireDate) // 设置jwt的过期时间
                .signWith(SignatureAlgorithm.HS512, "lalalalalalalalalalalalalalalalalalala") // 设置盐值和加密算法
                .compact();
    }
}
