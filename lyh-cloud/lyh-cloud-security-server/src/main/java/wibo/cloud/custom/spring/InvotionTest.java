package wibo.cloud.custom.spring;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname InvotionTest
 * @Description TODO
 * @Date 2021/4/29 9:32
 * @Created by lyh
 */
@Data
public class InvotionTest implements InvocationHandler {

    private String name;

    interface Test {
        void aaa();
    }

    public <T>T create(Class<T> t) {
       return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class[]{t},this);
    }

    public static void main(String[] args) {
        InvotionTest invotionTest = new InvotionTest();
        invotionTest.setName("asdasdasdad");
        Test test = invotionTest.create(Test.class);
        test.aaa();
        invotionTest.setName("bbbbbbbbb");
        test.aaa();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(name);
        return null;
    }
}
