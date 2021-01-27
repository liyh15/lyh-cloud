package wibo.cloud.custom.mybatis.config;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Classname MyBoundSql
 * @Description sql请求封装参数
 * @Date 2021/1/26 16:11
 * @Created by lyh
 */
@Data
@ToString
public class MyBoundSql {

    // 编译sql
    private String sql;

    // 入参集合
    private List<ParamBean> paramBeanList;

    // 参数值Map
    private Map<String, Object> paramValueMap;

    public MyBoundSql() {}

    public MyBoundSql(String sql, List<ParamBean> paramBeanList, Map<String, Object> paramValueMap) {
        this.sql = sql;
        this.paramBeanList = paramBeanList;
        this.paramValueMap = paramValueMap;
    }
}
