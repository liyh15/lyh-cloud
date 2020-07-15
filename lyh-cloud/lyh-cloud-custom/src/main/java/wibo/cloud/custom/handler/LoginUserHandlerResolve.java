package wibo.cloud.custom.handler;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wibo.cloud.custom.config.LoginAnnotation;
import wibo.cloud.custom.config.LoginUser;

/**
 * 这个主要对所有接口的入参进行一个拦截重赋值
 */
@Component
public class LoginUserHandlerResolve implements HandlerMethodArgumentResolver {

    /**
     * 拦截需要符合的条件
     * @param methodParameter 接口方法的入参包装类
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        boolean blag = (methodParameter.getParameterType().isAssignableFrom(LoginUser.class) && methodParameter.hasParameterAnnotation(LoginAnnotation.class));
        return  blag;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String userId = (String) nativeWebRequest.getAttribute("userId",0);
        LoginUser user = new LoginUser();
        user.setUserId(userId);
        return user;
    }
}
