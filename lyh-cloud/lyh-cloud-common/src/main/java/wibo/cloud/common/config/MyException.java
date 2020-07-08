package wibo.cloud.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常
 */
@Slf4j
@Data
public class MyException extends RuntimeException {

    private String code;

    private String message;

    public MyException(ErrorCode.Status status) {
       this.code = status.code;
       this.message = status.message;
    }

    public MyException(String code, String message) {
       this.code = code;
       this.message = message;
    }
}
