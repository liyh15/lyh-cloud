package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname ForeachDataBean
 * @Description foreach循环item数据类
 * @Date 2021/1/28 14:36
 * @Created by lyh
 */
@Data
@ToString
public class ForeachDataBean {

    // item名（来自于）
    private String item;

    // 别名(真实路径)
    private String realName;

    // 数据
    private Object data;


    public ForeachDataBean(String item, String realName, Object data) {
        this.realName = realName;
        this.item = item;
        this.data = data;
    }
}
