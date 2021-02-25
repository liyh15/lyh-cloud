package wibo.cloud.custom.spring;

import org.mybatis.spring.SqlSessionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.annotation.Annotation;

/**
 * @Classname spring
 * @Description spring底层测试类
 * @Date 2021/1/14 16:31
 * @Created by lyh
 */
@ComponentScan
public class SpringTest {
    public static void main(String[] args) {
        /* ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring-cfg.xml");*/
        // TODO AnnotationConfigApplicationContext是springboot使用的容器
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringTest.class);
        // TODO spring对Bean的初始化有三个步骤
        /*ConditionBean conditionBean = ctx.getBean(ConditionBean.class);
        conditionBean.test();
        SpringImportTestBean springImportTestBean = ctx.getBean(SpringImportTestBean.class);
        System.out.println(springImportTestBean);*/
        AspectBeanInter springImportTestBean = ctx.getBean(AspectBeanInter.class);
        springImportTestBean.aspectMethod();
    }
}
