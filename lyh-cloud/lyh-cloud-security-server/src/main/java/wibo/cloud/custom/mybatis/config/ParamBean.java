package wibo.cloud.custom.mybatis.config;

import wibo.cloud.custom.mybatis.typehandler.TypeHandler;

/**
 * @Classname ParamBean
 * @Description XML参数实体
 * @Date 2021/1/22 17:19
 * @Created by lyh
 */
public class ParamBean {

    // 参数类型 #,$
    private String paramType;

    // 参数名称
    private String paramName;

    // 对应的数据库类型
    private String jdbcType;

    // 对应的java类型
    private String javaType;

    // 类型转换器
    private TypeHandler typeHandler;
}
