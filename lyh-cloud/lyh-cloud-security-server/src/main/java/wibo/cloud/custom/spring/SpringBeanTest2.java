package wibo.cloud.custom.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Classname SringTest
 * @Description TODO
 * @Date 2020/10/13 15:56
 * @Created by lyh
 */

@Data
//@Component
public class SpringBeanTest2 implements BeanPostProcessor {

    // TODO 对于实现BeanPostprocessor本身的类的不会调用，并且也不会运行其他类实现的BeanPostprocessor

    private String name;

    private Integer age;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "开始进行初始化");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化结束");
        return bean;
    }
}
