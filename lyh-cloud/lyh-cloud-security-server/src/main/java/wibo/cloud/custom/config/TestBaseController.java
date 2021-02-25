package wibo.cloud.custom.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wibo.cloud.common.config.TestException;
import wibo.cloud.common.response.BaseResponse;

/**
 * @Classname TestBaseController
 * @Description TODO
 * @Date 2020/12/25 14:48
 * @Created by lyh
 */
@RestControllerAdvice
public class TestBaseController {

    /**
     * 抛出自定义异常
     * 这种自定义返回结果不会走HandlerMethodReturnValueHandler的RequestBody的处理
     * 会直接进行转换过去
     * @param exc
     * @return
     */
    @Advice
    @ExceptionHandler(value = TestException.class)
    @ResponseBody
    public BaseResponse exception(TestException exc) {
        System.out.println("bbbbb");
        BaseResponse response = new BaseResponse();
        return response;
    }
}
