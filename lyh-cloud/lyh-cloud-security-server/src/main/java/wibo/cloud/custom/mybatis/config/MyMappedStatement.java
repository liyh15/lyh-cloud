package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import jodd.util.StringUtil;
import org.dom4j.Element;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    // 属性集合
    private Map<String, String> attributeMap;

    // mapper标签
    private Element element;


    public MyMappedStatement(TypeEnume typeEnume, String nameSpace, Map<String, String> attributeMap, Element element) {
        this.typeEnume = typeEnume;
        this.nameSpace = nameSpace;
        this.attributeMap = attributeMap;
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public String getAttribute(String name) {
        return attributeMap.get(name);
    }

    public String getNameSpace() {
        return nameSpace;
    }
}
