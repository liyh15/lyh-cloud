package wibo.cloud.common.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorCode {
    public enum Status {

        SUCESS("200", "成功"),
        ERROR("601", "系统异常"),
        // 这里自定义异常
        MESSAGE_IS_NULL("201", "数据为空");

        public String code;
        public String message;

        Status(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
