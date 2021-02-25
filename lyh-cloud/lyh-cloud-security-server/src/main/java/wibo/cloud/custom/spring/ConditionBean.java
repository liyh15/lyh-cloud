package wibo.cloud.custom.spring;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Classname ConditionBean
 * @Description TODO
 * @Date 2021/1/15 14:36
 * @Created by lyh
 */
@Data
@ToString
// @Component
@Primary
public class ConditionBean {

    private String name = "liyuanhao";

    private Integer age = 52;

    @Autowired
    private SpringBeanTest springBeanTest;

    public void test() {
        System.out.println(springBeanTest);
    }
}
