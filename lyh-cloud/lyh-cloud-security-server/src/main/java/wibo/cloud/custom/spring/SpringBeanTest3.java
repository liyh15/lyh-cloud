package wibo.cloud.custom.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Classname SringTest
 * @Description TODO
 * @Date 2020/10/13 15:56
 * @Created by lyh
 */
@Data
//@Component
public class SpringBeanTest3 implements BeanPostProcessor {

    private String name;

    private Integer age;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "333开始进行初始化");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "333初始化结束");
        return bean;
    }
}
