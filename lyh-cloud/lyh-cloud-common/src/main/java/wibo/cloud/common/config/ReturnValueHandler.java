package wibo.cloud.common.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import wibo.cloud.common.response.BaseResponse;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ReturnValueHandler implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

    public ReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        log.error("returnValue:{}", returnValue);
        // 表示此handle处理请求，不需要交给其他handle处理
        mavContainer.setRequestHandled(true);
        HttpServletResponse httpServletResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        if (returnValue instanceof BaseResponse) {
            BaseResponse response = new BaseResponse();
            response.setCode(ErrorCode.Status.SUCESS.code);
            response.setMessage(ErrorCode.Status.SUCESS.message);
            response.setData(returnValue);
            httpServletResponse.getWriter().write(JSONObject.toJSONString(response));
        } else {
            httpServletResponse.getWriter().write(JSONObject.toJSONString(returnValue));
        }
    }
}
