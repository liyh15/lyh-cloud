package wibo.cloud.custom.mybatis.config;

import java.util.List;
import java.util.Map;

/**
 * @Classname MyMappedConfig
 * @Description 对应每个mapper每个sql语句的配置
 * @Date 2021/1/18 17:18
 * @Created by lyh
 */
public class MyMappedStatement {

    // 语句类型(增，删，改，查)
    private TypeEnume typeEnume;

    // xml全限名
    private String nameSpace;

    // 方法名称
    private String id;

    // 返回类型
    private Object resultType;

    // 参数类型
    private Object paramType;

    // 拥有参数集合
    private List<ParamBean> paramBeanList;


}
