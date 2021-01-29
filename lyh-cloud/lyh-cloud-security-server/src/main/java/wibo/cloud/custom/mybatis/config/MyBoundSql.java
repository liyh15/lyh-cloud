package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import jodd.util.StringUtil;
import lombok.Data;
import lombok.ToString;
import org.dom4j.Element;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // 匹配#{}
    private static Pattern regex1 = Pattern.compile("\\#\\{([^#${}]*)\\}");

    // 匹配${}
    private static Pattern regex2 = Pattern.compile("\\$\\{([^#${}]*)\\}");

    public MyBoundSql() {}

    public MyBoundSql(String sql, List<ParamBean> paramBeanList, Map<String, Object> paramValueMap) {
        this.sql = sql;
        this.paramBeanList = paramBeanList;
        this.paramValueMap = paramValueMap;
    }

    /**
     * 构建sql
     * @param paramObject
     * @param statement
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/29 17:28
     */
    public static MyBoundSql buildBoundSql(Object paramObject, MyMappedStatement statement) throws Exception {
        List<ParamBean> paramBeanList = new ArrayList<>(); // 按先后顺序保存请求参数
        Map<String, Object> paramValueMap = new HashMap<>(); // 保存参数名，参数的映射

        Element elementX = statement.getElement();
        Element rootElement = elementX.createCopy();
        List<FunctionElement> rootFunctionElementListList = new ArrayList<>();
        FunctionElement rootFunctionElement = new FunctionElement(rootElement);
        addFunctionElement(rootFunctionElement, rootFunctionElementListList, true);

        String text = rootElement.getText(); // 获取跟标签内容
        Matcher matcherBody1 = regex1.matcher(text);
        Matcher matcherBody2 = regex2.matcher(text);
        while (matcherBody1.find()) {
            String paramName = matcherBody1.group(1);
            paramValueMap.put(paramName, getParamMapValue(paramObject, paramName, statement.getNameSpace()));
        }

        for (FunctionElement element : rootFunctionElementListList) {
            // TODO 需要对每个Element应对各种类型做出判断
            switch (element.getTypeEnum()) {
                case FOREACH:
                    List<ForeachDataBean> foreachDataBeanList = new ArrayList<>();
                    handleForeach(element, paramObject, foreachDataBeanList, paramValueMap, statement);
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
        System.out.println(rootFunctionElement.getStringValue());
        return null;
    }

    /**
     * 处理foreach功能标签，在处理foreach的时候，需要把遍历的对象传过去，还有整个xml的入参对象
     * @param element foreach功能标签
     * @param paramObject 方法入参
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/28 10:02
     */
    private static void handleForeach(FunctionElement element, Object paramObject, List<ForeachDataBean> foreachDataBeanList,  Map<String, Object> paramValueMap,
        MyMappedStatement myMappedStatement) throws Exception {
        StringBuilder allText = new StringBuilder();
        String elementText = element.getText();
        List listObject = null; // foreach集合

        String itemStr = element.getAttributeValue("item"); // 获取属性名
        String collectionStr = element.getAttributeValue("collection"); // 获取集合名称
        String seperateStr = element.getAttributeValue("separator");
        String openStr = element.getAttributeValue("open");
        String closeStr = element.getAttributeValue("close");
        if (StringUtil.isNotBlank(openStr)) {
            allText.append(openStr);
        }

        // 集合实例别名
        String realName = "";
        // 有没有从collection获取的数据
        DataBean realDataBean = getDataBean(collectionStr, foreachDataBeanList, paramObject, myMappedStatement);
        realName = realDataBean.getRealName();
        listObject = (List) realDataBean.getData(); // 获取集合

        int size = listObject.size();
        for (int i = 0; i < size ; i ++) {
            Object obj = listObject.get(i);
            List<ForeachDataBean> dataBeanList = new ArrayList<>(foreachDataBeanList);
            ForeachDataBean foreachDataBean = new ForeachDataBean(itemStr, realName +"_"+ i, obj);
            dataBeanList.add(foreachDataBean);

            FunctionElement functionElement = element.copyElement(); // 当前标签的复制品
            // 处理所有子对象
            handleChildElement(functionElement.getChildElementList(), paramObject, dataBeanList, paramValueMap, myMappedStatement);

            String text = functionElement.getStringValue(); // 获取跟标签内容
            Matcher matcherBody1 = regex1.matcher(text);
            Matcher matcherBody2 = regex2.matcher(text);
            while (matcherBody1.find()) {
                String paramName = matcherBody1.group(1);
                DataBean dataBean = getDataBean(paramName, dataBeanList, paramObject, myMappedStatement);
                String paramRealName = dataBean.getRealName();
                Object objj = dataBean.getData();
                paramValueMap.put(paramRealName, objj);
                text = text.replaceAll("\\#\\{" + paramName + "}", "{" + paramName + "}");
            }
            allText.append(text).append(seperateStr);
        }
        allText.append(closeStr);
        String lastTest = allText.toString();
        if (StringUtil.isNotBlank(seperateStr)) {
            lastTest = lastTest.substring(0, lastTest.length() - 1);
        }
        element.setText(lastTest);
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
    private static void handleChildElement(List<FunctionElement> elementList, Object paramObject, List<ForeachDataBean> foreachDataBeanList, Map<String, Object> paramValueMap,
                                           MyMappedStatement myMappedStatement) throws Exception {
        if (CollUtil.isNotEmpty(elementList)) {
            for (FunctionElement element : elementList) {
                switch (element.getTypeEnum()) {
                    case FOREACH:
                        handleForeach(element, paramObject, foreachDataBeanList, paramValueMap, myMappedStatement);
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
     * 添加功能标签
     * @param
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/26 11:07
     */
    private static void addFunctionElement(FunctionElement rootElement, List<FunctionElement> rootFunctionElementListList, boolean isRoot) {
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
     * 获取实际参数代号和值
     * @param paramName
     * @param foreachDataBeanList
     * @param paramObject
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/29 11:06
     */
    private static DataBean getDataBean(String paramName, List<ForeachDataBean> foreachDataBeanList, Object paramObject, MyMappedStatement myMappedStatement) throws Exception {
        String paramRealName = null;
        Object objj = null;
        String [] paramNames = paramName.split(".");
        ForeachDataBean bean = getForeachDataBean(paramNames[0], foreachDataBeanList);
        if (ObjectUtil.isNull(bean)) {
            paramRealName = paramName;
            objj =  getParamMapValue(paramObject, paramRealName, myMappedStatement.getNameSpace());
        } else {
            paramRealName = bean.getRealName() + (paramName.indexOf(".") == -1 ? "" : paramName.substring(paramName.indexOf(".") + 1));
            objj = bean.getData();
        }
        DataBean dataBean = new DataBean(paramRealName, objj);
        return dataBean;
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
    private static ForeachDataBean getForeachDataBean(String name, List<ForeachDataBean> foreachDataBeanList) {
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
    private static Object getParamMapValue(Object paramObjec, String paramName, String nameSpace) throws Exception {
        if (ObjectUtil.isNull(paramObjec)) {
            throw new Exception(nameSpace + ": paramObjec is null");
        }
        if (paramObjec instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) paramObjec;
            String [] param = paramName.split(".");
            String paramHead = param[0]; // 这是参数头
            // 如果入参是Map格式
            if (!paramMap.containsKey(paramHead)) {
                throw new Exception(nameSpace + " paramName is not exist: " + paramName + ": Available parameters are " + paramMap);
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
