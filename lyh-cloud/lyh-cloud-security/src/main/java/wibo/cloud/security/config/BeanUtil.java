package wibo.cloud.security.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wibo.cloud.security.service.impl.TestServiceImpl;
import wibo.cloud.security.service.impl.TwoServiceImpl;

/**
 * @Classname BeanUtil
 * @Description TODO
 * @Date 2021/4/12 10:16
 * @Created by lyh
 */
@Configuration
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext1;


    // todo @Bean注解默认使用方法名作为beanName进行存储
    // TODO 类型可以相同，但是beanName不能相同
    @Bean
    public TwoServiceImpl twoServiceImpl() {
        System.out.println("asdasdqweqweqwe");
        return new TwoServiceImpl();
    }

    public static <T>T getBean(Class<T> name) {
        return applicationContext1.getBean(name);
    }

    public static Object getBeanByName(String name) {
        return applicationContext1.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext1 = applicationContext;
    }
}
