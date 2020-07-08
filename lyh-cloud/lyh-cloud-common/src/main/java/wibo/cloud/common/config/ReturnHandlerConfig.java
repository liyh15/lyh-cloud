package wibo.cloud.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@Order(1)
// 实现InitializingBean接口比CommandLineRunner实现的早
public class ReturnHandlerConfig implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取系统的返回值处理类的集合
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        for (HandlerMethodReturnValueHandler valueHandler : handlers) {
            if (valueHandler instanceof RequestResponseBodyMethodProcessor) {
                int index = handlers.indexOf(valueHandler);
                // 替换掉@ResponseBody处理类
                handlers.set(index, new ReturnValueHandler(valueHandler));
            }
        }
        adapter.setReturnValueHandlers(handlers);
    }
}
