package wibo.cloud.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * BaseResponse为返回基类，目的为了Feign调用的json解析和判断是否返回状态码
 */
@Data
@Slf4j
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseResponse<T> implements Serializable {

    public final static BaseResponse DEFAULT = new BaseResponse();
    /**
     * 返回码
     */
    private String code;
    /**
     * 状态说明
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;
}
