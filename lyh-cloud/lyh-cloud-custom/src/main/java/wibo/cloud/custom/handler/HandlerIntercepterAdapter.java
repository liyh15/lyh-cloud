package wibo.cloud.custom.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wibo.cloud.custom.config.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HandlerIntercepterAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            // 如果没有这个注解就不需要拦截
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            // 如果没有这个注解就不需要拦截
            return true;
        }
        // 获取请求头里面的token
        String token = request.getHeader("token");
        Claims claims =  Jwts.parser()
                .setSigningKey("lalalalalalalalalalalalalalalalalalalaAAA")
                .parseClaimsJws(token)
                .getBody();
        String userId = claims.getSubject();
        request.setAttribute("userId", userId);
        return true;
    }
}
