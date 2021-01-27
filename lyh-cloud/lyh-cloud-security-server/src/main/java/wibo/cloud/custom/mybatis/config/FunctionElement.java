package wibo.cloud.custom.mybatis.config;

import cn.hutool.core.collection.CollUtil;
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


    public String getText() {
        return element.getText();
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
