package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname ParamNameBeanList
 * @Description TODO
 * @Date 2021/1/25 11:30
 * @Created by lyh
 */
@Data
@ToString
public class ParamNameBean {

    /*
      参数名称
     */
    private String name;

    /*
      是否是集合
     */
    private boolean isList;
}
