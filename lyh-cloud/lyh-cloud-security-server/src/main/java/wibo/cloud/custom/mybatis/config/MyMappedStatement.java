package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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

    // 匹配#{}
    private static Pattern regex1 = Pattern.compile("\\#\\{([^}]*)\\}");

    // 匹配${}
    private static Pattern regex2 = Pattern.compile("\\$\\{([^}]*)\\}");


    public MyMappedStatement(TypeEnume typeEnume, String nameSpace, Map<String, String> attributeMap, Element element) {
        this.typeEnume = typeEnume;
        this.nameSpace = nameSpace;
        this.attributeMap = attributeMap;
        this.element = element;
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
    private void addFunctionElement(FunctionElement rootElement, List<FunctionElement> rootFunctionElementListList, boolean isRoot) {
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
                addFunctionElement(functionElement,rootFunctionElementListList, false);
            }
        }
    }

    /**
     * 构造sql
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/26 15:28
     */
    public MyBoundSql buildBoundSql(Object paramObject) throws Exception {
        List<ParamBean> paramBeanList = new ArrayList<>(); // 按先后顺序保存请求参数
        Map<String, Object> paramValueMap = new HashMap<>(); // 保存参数名，参数的映射

        Element rootElement = element.createCopy();
        List<FunctionElement> rootFunctionElementListList = new ArrayList<>();
        FunctionElement rootFunctionElement = new FunctionElement(rootElement);
        addFunctionElement(rootFunctionElement, rootFunctionElementListList, true);

        String text = rootElement.getText(); // 获取跟标签内容
        Matcher matcherBody1 = regex1.matcher(text);
        Matcher matcherBody2 = regex2.matcher(text);
        while (matcherBody1.find()) {
            String paramName = matcherBody1.group(1);
            paramValueMap.put(paramName, getParamMapValue(paramObject, paramName));
        }

        for (FunctionElement element : rootFunctionElementListList) {
             // TODO 需要对每个Element应对各种类型做出判断
            switch (element.getTypeEnum()) {
                case FOREACH:
                    String listParamName = element.getAttributeValue("collection");
                    List list = (List) getParamMapValue(paramObject, listParamName); // 获取集合
                    List<ForeachDataBean> foreachDataBeanList = new ArrayList<>();
                    handleForeach(element, list, paramObject, foreachDataBeanList, paramValueMap);
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
        }
        return null;
    }

    /**
     * 处理foreach功能标签，在处理foreach的时候，需要把遍历的对象传过去，还有整个xml的入参对象
     * @param element foreach功能标签
     * @param listObject foreach功能标签的集合对象
     * @param paramObject 方法入参
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/28 10:02
     */
    private void handleForeach(FunctionElement element, List listObject, Object paramObject, List<ForeachDataBean> foreachDataBeanList,  Map<String, Object> paramValueMap) throws Exception {
        StringBuilder allText = new StringBuilder();
        String elementText = element.getText();


        String itemStr = element.getAttributeValue("item"); // 获取属性名
        String collectionStr = element.getAttributeValue("collection"); // 获取集合名称
        String seperateStr = element.getAttributeValue("separator");
        String openStr = element.getAttributeValue("open");
        String closeStr = element.getAttributeValue("close");


        // 获得foreach集合实际循环的数据
        String [] collections = collectionStr.split(".");
        // 集合实际别名
        String realName = "";
        // 有没有从collection获取的数据
        ForeachDataBean foreachDataBean = getForeachDataBean(collections[0], foreachDataBeanList);
        if (ObjectUtil.isNull(foreachDataBean)) {
            realName = collectionStr;
        } else {
            realName = foreachDataBean.getRealName() + (collectionStr.indexOf(".") == -1 ? "" : collectionStr.substring(collectionStr.indexOf(".") + 1));
        }

        int size = listObject.size();
        for (int i = 0; i < size ; i ++) {
            Object obj = listObject.get(i);
            List<ForeachDataBean> dataBeanList = new ArrayList<>(foreachDataBeanList);
            ForeachDataBean dataBean = new ForeachDataBean(itemStr, realName +"_"+ i, obj);
            dataBeanList.add(foreachDataBean);
            // 处理所有子对象
            handleChildElement(element.getChildElementList(), paramObject, dataBeanList);
        }
    }


    /**
     * 处理标签所有的子标签
     * @param elementList
     * @param paramObject
     * @param foreachDataBeanList
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/28 14:58
     */
    private void handleChildElement(List<FunctionElement> elementList, Object paramObject, List<ForeachDataBean> foreachDataBeanList) {
        if (CollUtil.isNotEmpty(elementList)) {
            for (FunctionElement element : elementList) {
                switch (element.getTypeEnum()) {
                    case FOREACH:
                        String listParamName = element.getAttributeValue("collection");
                        // List list = (List) getParamMapValue(paramObjec, listParamName); // 获取集合
                        // handleForeach(element, list);
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
            }
        }
    }

    /**
     * 从foreach获取循环数据类型
     * @param name
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/28 15:39
     */
    private ForeachDataBean getForeachDataBean(String name, List<ForeachDataBean> foreachDataBeanList) {
         if (CollUtil.isNotEmpty(foreachDataBeanList)) {
             int size = foreachDataBeanList.size();
             for (int i = (size - 1); i >= 0; i --) {
                 ForeachDataBean dataBean = foreachDataBeanList.get(i);
                 if (name.equals(dataBean.getItem())) {
                     return dataBean;
                 }
             }
         }
         return null;
    }

   /**
    * 如果为Map时获取实体参数
    * @param paramName xml参数字符串#{str}
    * @return
    * @throws
    * @description
    * @author liyuanhao
    * @date 2021/1/27 9:46
    */
    private Object getParamMapValue(Object paramObjec, String paramName) throws Exception {
        if (ObjectUtil.isNull(paramObjec)) {
            throw new Exception(nameSpace + ": paramObjec is null");
        }
        if (paramObjec instanceof Map) {
            Map<String, Object> paramMap = null;
            paramMap = (Map<String, Object>) paramObjec;
            String [] param = paramName.split(".");
            String paramHead = param[0]; // 这是参数头
            // 如果入参是Map格式
            if (!paramMap.containsKey(paramHead)) {
                throw new Exception(nameSpace + " paramName is not exist: " + paramName);
            }
            Object obj = paramMap.get(paramHead); // 获取一层类
            if (param.length == 1) {
               return obj;
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
            return obj;
        } else {
            Class cls = paramObjec.getClass();
            String [] param = paramName.split(".");
            if (param.length == 1) {
                if (cls.isPrimitive()) {
                    return paramObjec;
                } else {
                    Field field = cls.getField(param[0]);
                    field.setAccessible(true);
                    return field.get(paramObjec);
                }
            } else {
                if (cls.isPrimitive()) {
                    throw new Exception(nameSpace + " paramName is not exist: " + paramName);
                } else {
                    int index = 0;
                    while ((index + 1) <= param.length) {
                        String fieldName = param[index];
                        Field field = cls.getField(fieldName);
                        field.setAccessible(true);
                        paramObjec = field.get(paramObjec);
                        index ++;
                    }
                }
                return paramObjec;
            }
        }
    }
}
