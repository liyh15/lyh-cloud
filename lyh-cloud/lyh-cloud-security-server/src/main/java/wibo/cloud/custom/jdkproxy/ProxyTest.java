package wibo.cloud.custom.jdkproxy;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname ProxyTest
 * @Description TODO
 * @Date 2020/8/5 16:21
 * @Created by lyh
 */
public class ProxyTest implements InvocationHandler {

    private Object o;

    public ProxyTest(Object o) {
       this.o = o;
    }

    public <T> T  makeOb(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("this is proxy class");
        return method.invoke(o);
    }

    public static void main(String[] args) {
        ProxyClass proxyClass = new ProxyClass();
        ProxyTest proxyTest = new ProxyTest(TestInterface.class);
        TestInterface testInterface = proxyTest.makeOb(TestInterface.class);
    }
}
