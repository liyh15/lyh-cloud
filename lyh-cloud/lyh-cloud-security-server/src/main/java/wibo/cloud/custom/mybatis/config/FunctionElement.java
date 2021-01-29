package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.dom4j.Attribute;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MyElement
 * @Description 自定义xml标签类
 * @Date 2021/1/27 11:10
 * @Created by lyh
 */
public class FunctionElement {

    /*
     父类Xml标签
     */
    private FunctionElement parentElement;

    /*
      子功能标签集合
     */
    private List<FunctionElement> childElementList;

    /*
      功能标签类型
     */
    private MyElementTypeEnum typeEnum;

    /*
     代理的功能标签对象
     */
    private Element element;

    /**
     *  复制本类
     * @param
     * @return 
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/28 17:50
     */ 
    public FunctionElement copyElement() {
        Element copyElement =  element.createCopy();
        FunctionElement element = new FunctionElement(copyElement);
        element.setParentElement(parentElement);
        buildChild(element);
        return element;
    }

    private void buildChild(FunctionElement copyElement) {
        List<Element> elementList = copyElement.elements();
        if (CollUtil.isNotEmpty(elementList)) {
            for (Element element : elementList) {
                FunctionElement functionElement = new FunctionElement(element);
                functionElement.setParentElement(copyElement);
                // 设置功能标签类型
                functionElement.setTypeEnum(MyElementTypeEnum.getMyElementTypeEnum(element.getName()));
                // 添加功能标签的子对象
                copyElement.addChilElement(functionElement);
                buildChild(functionElement);
            }
        }
    }

    public void setText(String text) {
        element.setText(text);
    }

    public String getStringValue() {
        return element.getStringValue();
    }

    public Element getElement() {
        return element;
    }

    public String getText() {
        return element.getText();
    }

    public String getAttributeValue(String name) {
        Attribute attribute = element.attribute(name);
        return ObjectUtil.isNull(attribute) ? null : attribute.getValue();
    }

    public List elements() {
        return element.elements();
    }

    public FunctionElement(Element element) {
        this.element = element;
    }

    /**
     * 添加子功能标签
     * @param functionElement
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/27 15:54
     */
    public void addChilElement(FunctionElement functionElement) {
         if (CollUtil.isNotEmpty(childElementList)) {
             childElementList.add(functionElement);
         } else {
             childElementList = new ArrayList<>();
             childElementList.add(functionElement);
         }
    }

    /**
     * 返回子功能标签集合
     * @param
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2021/1/27 15:56
     */
    public List<FunctionElement> getChildElementList() {
        return childElementList;
    }

    public MyElementTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(MyElementTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public FunctionElement getParentElement() {
        return parentElement;
    }

    public void setParentElement(FunctionElement parentElement) {
        this.parentElement = parentElement;
    }
}
