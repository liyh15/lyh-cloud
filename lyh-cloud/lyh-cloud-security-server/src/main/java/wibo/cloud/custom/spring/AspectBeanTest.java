package wibo.cloud.custom.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @Classname AspectBeanTest
 * @Description TODO
 * @Date 2021/1/18 11:06
 * @Created by lyh
 */
@Component
@ComponentScan
@EnableAspectJAutoProxy
public class AspectBeanTest implements AspectBeanInter {

    @Override
    public void aspectMethod() {
        System.out.println("***这是测试方法体***");
    }
}
