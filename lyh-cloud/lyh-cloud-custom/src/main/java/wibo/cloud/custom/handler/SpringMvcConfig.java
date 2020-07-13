package wibo.cloud.custom.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    public LoginUserHandlerResolve loginUserHandlerResolve;

    @Autowired
    public HandlerIntercepterAdapter handlerIntercepterAdapter;

    /**
     * 添加接口入参拦截类
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserHandlerResolve);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerIntercepterAdapter);
    }
}
