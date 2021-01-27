package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
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

    // 根功能标签集合
    private List<FunctionElement> rootFunctionElementListList = new ArrayList<>();

    // 匹配#{}
    private static Pattern regex1 = Pattern.compile("\\#\\{([^}]*)\\}");

    // 匹配${}
    private static Pattern regex2 = Pattern.compile("\\$\\{([^}]*)\\}");


    public MyMappedStatement(TypeEnume typeEnume, String nameSpace, Map<String, String> attributeMap, Element element) {
        this.typeEnume = typeEnume;
        this.nameSpace = nameSpace;
        this.attributeMap = attributeMap;
        this.element = element;
        FunctionElement functionElement = new FunctionElement(element);
        addFunctionElement(functionElement, true);
    }

    /**
     * 添加功能标签
     * @param
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/26 11:07
     */
    private void addFunctionElement(FunctionElement rootElement, boolean isRoot) {
        List<Element> elementList = rootElement.elements();
        if (CollUtil.isNotEmpty(elementList)) {
            for (Element element : elementList) {
                FunctionElement functionElement = new FunctionElement(element);
                functionElement.setParentElement(rootElement);
                // 设置功能标签类型
                functionElement.setTypeEnum(MyElementTypeEnum.getMyElementTypeEnum(element.getName()));
                // 添加功能标签的子对象
                rootElement.addChilElement(functionElement);
                if (isRoot) {
                    rootFunctionElementListList.add(functionElement);
                }
                addFunctionElement(functionElement, false);
            }
        }
    }

    /**
     * 构造sql
     * @param paramObjec
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/26 15:28
     */
    public MyBoundSql buildBoundSql(Object paramObjec) throws Exception {
        Map<String, Object> paramMap = null;
        Class cls = paramObjec.getClass();
        if (paramObjec instanceof Map) {
            paramMap = (Map<String, Object>) paramObjec;
        }
        List<ParamBean> paramBeanList = new ArrayList<>(); // 按先后顺序保存请求参数
        Map<String, Object> paramValueMap = new HashMap<>(); // 保存参数名，参数的映射

        String text = element.getText();
        Matcher matcherBody1 = regex1.matcher(text);
        Matcher matcherBody2 = regex2.matcher(text);
        while (matcherBody1.find()) {
            String paramName = matcherBody1.group(1);
            if (CollUtil.isNotEmpty(paramMap)) {
                // 如果请求参数时Map类型
                getParamMapValueByMap(paramMap, paramValueMap, paramName);
            } else {
                // 如果请求参数只有一个
                getParamMapValueByObject(paramValueMap, paramName, paramObjec);
            }
        }

        for (FunctionElement element : rootFunctionElementListList) {
             // TODO 需要对每个Element应对各种类型做出判断
            switch (element.getTypeEnum()) {
                case FOREACH:
                    
                    break;
                case IF:
                    break;
                case WHERE:
                    break;
                case SET:
                    break;
                case TRIM:
                    break;
                default:
                    break;
            }

             String elementText = element.getText();
             Matcher matcher1 = regex1.matcher(elementText);
             while (matcher1.find()) {
                 String paramName = matcher1.group(1);
                 if (CollUtil.isNotEmpty(paramMap)) {
                     // 如果请求参数时Map类型
                     getParamMapValueByMap(paramMap, paramValueMap, paramName);
                 } else {
                     getParamMapValueByObject(paramValueMap, paramName, paramObjec);
                 }
             }
        }
        return null;
    }

   /**
    * 如果为Map时获取实体参数
    * @param paramMap 请求参数Map
    * @param paramValueMap xml参数映射请求参数Map
    * @param paramName xml参数字符串#{str}
    * @return
    * @throws
    * @description
    * @author liyuanhao
    * @date 2021/1/27 9:46
    */
    private void getParamMapValueByMap(Map<String, Object> paramMap, Map<String, Object> paramValueMap, String paramName) throws Exception {

        String [] param = paramName.split(".");
        String paramHead = param[0]; // 这是参数头
        // 如果入参是Map格式
        if (!paramMap.containsKey(paramHead)) {
            throw new Exception(nameSpace + " paramName is not exist: " + paramName);
        }
        Object obj = paramMap.get(paramHead); // 获取一层类
        if (param.length == 1) {
            paramValueMap.put(paramName, obj);
        }
        int index = 1;
        while ((index + 1) <= param.length) {
            String fieldName = param[index];
            Class cls = obj.getClass();
            Field field = cls.getField(fieldName);
            field.setAccessible(true);
            Object res = field.get(obj);
            obj = res;
            index ++;
        }
        paramValueMap.put(paramName, obj);
    }

    /**
     * 通过入参对象查询
     * @param paramValueMap xml参数映射请求参数Map
     * @param paramName xml对应的请求参数#{}
     * @param obj 接口入参实体
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/27 10:24
     */
    private void getParamMapValueByObject(Map<String, Object> paramValueMap, String paramName, Object obj) throws Exception {

        if (ObjectUtil.isNull(obj)) {
            throw new Exception(nameSpace + ": paramObjec is null");
        }
        String [] param = paramName.split(".");
        if (param.length == 1) {
            paramValueMap.put(paramName, obj);
        }
        int index = 0;
        while ((index + 1) <= param.length) {
            String fieldName = param[index];
            Class cls = obj.getClass();
            Field field = cls.getField(fieldName);
            field.setAccessible(true);
            Object res = field.get(obj);
            obj = res;
            index ++;
        }
        paramValueMap.put(paramName, obj);
    }
}
