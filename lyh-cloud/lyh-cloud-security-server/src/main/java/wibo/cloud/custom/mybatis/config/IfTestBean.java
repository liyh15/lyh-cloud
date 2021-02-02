package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname IfTestBean
 * @Description if test逻辑
 * @Date 2021/2/2 9:21
 * @Created by lyh
 */
@Data
@ToString
public class IfTestBean {

    private int start;

    private int length;

    public IfTestBean(int start, int length) {
        this.start = start;
        this.length = length;
    }

    public void addStart() {
        start++;
    }
}
