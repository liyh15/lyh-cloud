package wibo.cloud.custom.handler;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import wibo.cloud.common.config.TestException;
import wibo.cloud.custom.config.Advice;

/**
 * @Classname ResponseAdvoce
 * @Description TODO
 * @Date 2020/12/25 14:38
 * @Created by lyh
 */
@ControllerAdvice
public class ResponseAdvoce implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return ObjectUtil.isNotNull(methodParameter.getMethodAnnotation(Advice.class));
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            System.out.println("aaaaaaaaaaaaaaa");
            int a = 1 / 0;
        } catch (Exception e) {
          throw new TestException();
        }
        return o;
    }
}
