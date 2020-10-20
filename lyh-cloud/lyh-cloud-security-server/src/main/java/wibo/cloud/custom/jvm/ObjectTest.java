package wibo.cloud.custom.jvm;

import java.lang.reflect.Field;

/**
 * @Classname ObjectTest
 * @Description TODO
 * @Date 2020/9/29 15:45
 * @Created by lyh
 */
public class ObjectTest {

    private String name = "asdasd";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        /*Class cls = ObjectTest.class;
        ObjectTest test = new ObjectTest();
        Field field = cls.getDeclaredField("name");
        System.out.println(field.get(test));
        field.setAccessible(true);
        field.set(test, "aaaaaaaaaaaaaaaaaa");
        System.out.println(field.get(test));*/
        String a = "aaa";
        ObjectTest test1 = new ObjectTest();
        ObjectTest test2 = new ObjectTest();
        Class cls = test1.getClass();
        System.out.println(cls.getClassLoader());
    }
}
