package wibo.cloud.common.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class TestResponse extends BaseResponse<TestResponse> {

    private String name;

    private String message;
}
