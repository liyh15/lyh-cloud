package wibo.cloud.common.response;

import lombok.Data;
import wibo.cloud.common.pojo.Test;

import java.util.List;

@Data
public class MySqlTestResponse extends BaseResponse<MySqlTestResponse>{

    private List<Test> testList;
}
