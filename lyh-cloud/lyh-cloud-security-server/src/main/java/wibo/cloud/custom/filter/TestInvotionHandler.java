package wibo.cloud.custom.filter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname TestInvotionHandler
 * @Description TODO
 * @Date 2021/1/20 19:30
 * @Created by lyh
 */
public class TestInvotionHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("asdadasdasd");
        return "123";
    }

    public static void main(String[] args) {
        InvoInterface invoInterface = (InvoInterface) Proxy.newProxyInstance(InvoInterface.class.getClassLoader(), new Class[]{InvoInterface.class}, new TestInvotionHandler());
        invoInterface.aaa();
    }
}
