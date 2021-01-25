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

    // 入参类型
    private Object paramType;

    // 属性集合
    private Map<String, String> attributeMap;

    // 拥有参数集合(这个需要动态生成，用于设置参数时使用)
    private List<ParamBean> paramBeanList;

    // 参数名集合，Sql所有参数汇总
    private List<ParamNameBean> paramNameBeanList;
}
