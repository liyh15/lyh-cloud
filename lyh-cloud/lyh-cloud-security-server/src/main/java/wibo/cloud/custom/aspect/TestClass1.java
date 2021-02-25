package wibo.cloud.custom.aspect;

import org.springframework.stereotype.Component;

/**
 * @Classname TestClass1
 * @Description TODO
 * @Date 2020/11/19 16:06
 * @Created by lyh
 */
@Component("TestClass1")
public class TestClass1 implements TestInterface {

    
    @Override
    public void aaa(String a) {
        System.out.println("aaaaaaaaaaaa");
    }
}
