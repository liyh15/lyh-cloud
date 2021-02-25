package wibo.cloud.custom.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @Classname SringTest
 * @Description TODO
 * @Date 2020/10/13 15:56
 * @Created by lyh
 */
@Data
@ComponentScan
//@Component
@Import(SpringImportTestBean.class)
public class SpringBeanTest implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    private String name = "hzy";

    private Integer age = 123123123;

    @Override
    public void setBeanName(String s) {
        System.out.println("这个是bean的名称" + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("这是BeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("这是获取ApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("这是获取afterPropertiesSet");
    }

    @Bean
    @Conditional(ConditionTestBean.class)
    public ConditionBean returnBean() {
        return new ConditionBean();
    }
}
