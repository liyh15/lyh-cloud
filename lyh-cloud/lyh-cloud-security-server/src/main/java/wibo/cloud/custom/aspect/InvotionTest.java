package wibo.cloud.custom.aspect;

import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Around;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Classname InvotionTest
 * @Description TODO
 * @Date 2021/1/8 14:36
 * @Created by lyh
 */
public class InvotionTest implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("aaaaaaaaaaaaaaaaa");
        Parameter parameter = method.getParameters()[0];
        System.out.println(parameter.getName());
        System.out.println(parameter.getType());
        System.out.println(parameter.getDeclaredAnnotation(Around.class));
        System.out.println(parameter.getDeclaredAnnotation(Param.class).value());
        return null;
    }

    public static void main(String[] args) {
        TestInterface testInterface = (TestInterface) Proxy.newProxyInstance(TestInterface.class.getClassLoader(), new Class[]{TestInterface.class}, new InvotionTest());
        testInterface.aaa("asdasd");
    }
}
