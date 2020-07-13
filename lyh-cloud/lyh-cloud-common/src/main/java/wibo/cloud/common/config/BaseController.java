package wibo.cloud.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import wibo.cloud.common.response.BaseResponse;

@ControllerAdvice
@Slf4j
public class BaseController {

    /**
     * 抛出系统异常
     * @param exc
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exception(Exception exc) {
        log.error("", exc);
        BaseResponse response = new BaseResponse();
        response.setCode(ErrorCode.Status.ERROR.code);
        response.setMessage(exc.toString());
        log.error("", exc);
        return response;
    }

    /**
     * 抛出自定义异常
     * 这种自定义返回结果不会走HandlerMethodReturnValueHandler的RequestBody的处理
     * 会直接进行转换过去
     * @param exc
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public BaseResponse exception(MyException exc) {
        BaseResponse response = new BaseResponse();
        response.setCode(exc.getCode());
        response.setMessage(exc.getMessage());
        return response;
    }
}
