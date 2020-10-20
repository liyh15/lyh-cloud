package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TestDemo
 * @Description TODO
 * @Date 2020/9/14 14:54
 * @Created by lyh
 */
public class TestDemo {

    public void one() {
        System.out.println("aaa");
    }

    public void two(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
       String a = "aaa";
       String c = a + "bbb";
       String b = c.intern();
       System.out.println(c == b);
    }
}
