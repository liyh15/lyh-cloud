package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname DataBean
 * @Description 实际数据类
 * @Date 2021/1/29 10:55
 * @Created by lyh
 */
@Data
@ToString
public class DataBean {

    private String realName;

    private Object data;

    public DataBean(String realName, Object data) {
        this.realName = realName;
        this.data = data;
    }
}
