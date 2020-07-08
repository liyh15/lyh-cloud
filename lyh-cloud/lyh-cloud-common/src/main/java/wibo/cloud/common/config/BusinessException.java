package wibo.cloud.common.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 抛出异常工具类
 */
@Slf4j
public class BusinessException {

    /**
     * 抛出指定类型异常
     * @param status
     */
    public static void throwException(ErrorCode.Status status) {
        throw new MyException(status);
    }

    /**
     * 抛出指定的异常
     * @param code 异常编码
     * @param message 异常说明
     */
    public static void throwExceptionByStatus(String code, String message) {
        throw new MyException(code, message);
    }
}
