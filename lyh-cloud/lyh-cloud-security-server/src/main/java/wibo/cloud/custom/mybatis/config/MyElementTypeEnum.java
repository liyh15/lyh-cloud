package wibo.cloud.custom.mybatis.config;

/**
 * @Classname MyElementTypeEnum
 * @Description 功能标签类型枚举
 * @Date 2021/1/27 14:23
 * @Created by lyh
 */
public enum MyElementTypeEnum {

    FOREACH,
    IF,
    WHERE,
    SET,
    TRIM;

    public static MyElementTypeEnum getMyElementTypeEnum(String name) {
        if ("foreach".equals(name)) {
            return FOREACH;
        } else if ("if".equals(name)) {
            return IF;
        } else if ("where".equals(name)) {
            return WHERE;
        } else if ("set".equals(name)) {
            return SET;
        } else if ("trim".equals(name)) {
            return TRIM;
        } else {
            return null;
        }
    }
}
