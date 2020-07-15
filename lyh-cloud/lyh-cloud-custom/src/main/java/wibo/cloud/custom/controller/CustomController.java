package wibo.cloud.custom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.common.config.BusinessException;
import wibo.cloud.common.config.ErrorCode;
import wibo.cloud.common.response.BaseResponse;

@RestController
@RefreshScope
@RequestMapping("/r")
public class CustomController {

    @Value("${parmas.name}")
    private String name;

    @Value("${parmas.body}")
    private String body;

    @RequestMapping("ccccccccccccccccccccccccccc")
    // @PreAuthorize("hasAuthority('p1')")
    public BaseResponse body() {
        return BaseResponse.DEFAULT;
    }
    
    @RequestMapping("name")
    //@PreAuthorize("hasAuthority('p2')")
    public BaseResponse name() {
        BusinessException.throwException(ErrorCode.Status.MESSAGE_IS_NULL);
        return BaseResponse.DEFAULT;
    }
    
    /**
     * @param name
     * @throws
     * @author lyh
     * @date 2020/7/15 18:00
     */
    @RequestMapping("getName")
    // @PreAuthorize("hasAuthority('p2')")
    public BaseResponse getName(String name) {
        System.out.println(name);
        return BaseResponse.DEFAULT;
    }
}
