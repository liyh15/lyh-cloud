package wibo.cloud.custom.jvm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname InvotionTest
 * @Description TODO
 * @Date 2020/10/20 11:08
 * @Created by lyh
 */
public class InvotionTest<T> implements InvocationHandler {

    private Map<Method, String> map;

    private  Class<T> cls;

    public T buildObj() {
        return (T) Proxy.newProxyInstance(this.cls.getClassLoader(), new Class[]{this.cls},this);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return map.get(method);
    }

    public static void main(String[] args) {
        Map<Method, String> map = new HashMap<>();
        Method [] methods = InvotionTestMapper.class.getMethods();
        for (Method method : methods) {
            map.put(method, method.getName());
        }
        InvotionTest<InvotionTestMapper> test = new InvotionTest();
        test.setCls(InvotionTestMapper.class);
        test.setMap(map);
        InvotionTestMapper mapper = test.buildObj();
        System.out.println(mapper.one());
        System.out.println(mapper.two());
    }
}
